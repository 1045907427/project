/**
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-01-22 chenwei 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据异常规则定义
 * @author chenwei
 */
public class DataException implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 单据名称(主表中文名)
     */
    private String name;

    /**
     * 主表表名
     */
    private String mtable;

    /**
     * 主表字段
     */
    private String mcolumn;

    /**
     * 关联表表名
     */
    private String rtable;

    /**
     * 关联表字段
     */
    private String rcolumn;

    /**
     * 主表关联字段
     */
    private String mrelatecolumn;

    /**
     * 关联表关联字段
     */
    private String rrelatecolumn;

    /**
     * 校验取值类型1固定值2其他表
     */
    private String type;

    /**
     * 值（类型为2其他表时为空）
     */
    private BigDecimal val;

    /**
     * 正常范围上限
     */
    private BigDecimal normalup;

    /**
     * 正常范围下限
     */
    private BigDecimal normaldown;

    /**
     * 异常范围上限
     */
    private BigDecimal exceptionup;

    /**
     * 异常范围下限
     */
    private BigDecimal exceptiondown;

    /**
     * 异常时提醒信息
     */
    private String exremind;

    /**
     * 状态1启用0停用
     */
    private String state;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * @return 主键
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 单据名称(主表中文名)
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            单据名称(主表中文名)
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 主表表名
     */
    public String getMtable() {
        return mtable;
    }

    /**
     * @param mtable 
	 *            主表表名
     */
    public void setMtable(String mtable) {
        this.mtable = mtable == null ? null : mtable.trim();
    }

    /**
     * @return 主表字段
     */
    public String getMcolumn() {
        return mcolumn;
    }

    /**
     * @param mcolumn 
	 *            主表字段
     */
    public void setMcolumn(String mcolumn) {
        this.mcolumn = mcolumn == null ? null : mcolumn.trim();
    }

    /**
     * @return 关联表表名
     */
    public String getRtable() {
        return rtable;
    }

    /**
     * @param rtable 
	 *            关联表表名
     */
    public void setRtable(String rtable) {
        this.rtable = rtable == null ? null : rtable.trim();
    }

    /**
     * @return 关联表字段
     */
    public String getRcolumn() {
        return rcolumn;
    }

    /**
     * @param rcolumn 
	 *            关联表字段
     */
    public void setRcolumn(String rcolumn) {
        this.rcolumn = rcolumn == null ? null : rcolumn.trim();
    }

    /**
     * @return 主表关联字段
     */
    public String getMrelatecolumn() {
        return mrelatecolumn;
    }

    /**
     * @param mrelatecolumn 
	 *            主表关联字段
     */
    public void setMrelatecolumn(String mrelatecolumn) {
        this.mrelatecolumn = mrelatecolumn == null ? null : mrelatecolumn.trim();
    }

    /**
     * @return 关联表关联字段
     */
    public String getRrelatecolumn() {
        return rrelatecolumn;
    }

    /**
     * @param rrelatecolum 
	 *            关联表关联字段
     */
    public void setRrelatecolumn(String rrelatecolumn) {
        this.rrelatecolumn = rrelatecolumn == null ? null : rrelatecolumn.trim();
    }

    /**
     * @return 校验取值类型1固定值2其他表
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            校验取值类型1固定值2其他表
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 值（类型为2其他表时为空）
     */
    public BigDecimal getVal() {
        return val;
    }

    /**
     * @param val 
	 *            值（类型为2其他表时为空）
     */
    public void setVal(BigDecimal val) {
        this.val = val;
    }

    /**
     * @return 正常范围上限
     */
    public BigDecimal getNormalup() {
        return normalup;
    }

    /**
     * @param normalup 
	 *            正常范围上限
     */
    public void setNormalup(BigDecimal normalup) {
        this.normalup = normalup;
    }

    /**
     * @return 正常范围下限
     */
    public BigDecimal getNormaldown() {
        return normaldown;
    }

    /**
     * @param normaldown 
	 *            正常范围下限
     */
    public void setNormaldown(BigDecimal normaldown) {
        this.normaldown = normaldown;
    }

    /**
     * @return 异常范围上限
     */
    public BigDecimal getExceptionup() {
        return exceptionup;
    }

    /**
     * @param exceptionup 
	 *            异常范围上限
     */
    public void setExceptionup(BigDecimal exceptionup) {
        this.exceptionup = exceptionup;
    }

    /**
     * @return 异常范围下限
     */
    public BigDecimal getExceptiondown() {
        return exceptiondown;
    }

    /**
     * @param exceptiondown 
	 *            异常范围下限
     */
    public void setExceptiondown(BigDecimal exceptiondown) {
        this.exceptiondown = exceptiondown;
    }

    /**
     * @return 异常时提醒信息
     */
    public String getExremind() {
        return exremind;
    }

    /**
     * @param exremind 
	 *            异常时提醒信息
     */
    public void setExremind(String exremind) {
        this.exremind = exremind == null ? null : exremind.trim();
    }

    /**
     * @return 状态1启用0停用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1启用0停用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
     * @return 添加人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 修改人用户编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人用户编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }
}