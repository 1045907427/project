package com.hd.agent.journalsheet.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class ContractCaluse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 条款名称
     */
    private String name;

    /**
     * 条款类型
     */
    private String type;

    /**
     * 费用类型:0.可变费用,1.固定费用
     */
    private String costtype;

    /**
     * 分摊方式:0.一次性分摊,1.分月平坦
     */
    private String sharetype;

    /**
     * 返还类型:0.月返,1.季返,2.年返
     */
    private String returntype;

    /**
     * 返还月份
     */
    private Integer returnmonthnum;

    /**
     * 返还要求:0.无要求,1.销售达成
     */
    private String returnrequire;

    /**
     * 对应费用科目
     */
    private String subjectexpenses;

    /**
     * 状态
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
     */
    private String addusername;

    /**
     * 制单人部门编号
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     */
    private String adddeptname;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 审核人编号
     */
    private String audituserid;

    /**
     * 审核人名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 中止人编号
     */
    private String stopuserid;

    /**
     * 中止人名称
     */
    private String stopusername;

    /**
     * 中止时间
     */
    private Date stoptime;

    /**
     * 关闭时间
     */
    private Date closetime;

    /**
     * 打印次数
     */
    private Integer printtimes;

    /**
     * 打印时间
     */
    private Date printdatetime;

    /**
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 条款名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            条款名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 条款类型
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            条款类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 费用类型:0.可变费用,1.固定费用
     */
    public String getCosttype() {
        return costtype;
    }

    /**
     * @param costtype 
	 *            费用类型:0.可变费用,1.固定费用
     */
    public void setCosttype(String costtype) {
        this.costtype = costtype == null ? null : costtype.trim();
    }

    /**
     * @return 分摊方式:0.一次性分摊,1.分月平坦
     */
    public String getSharetype() {
        return sharetype;
    }

    /**
     * @param sharetype 
	 *            分摊方式:0.一次性分摊,1.分月平坦
     */
    public void setSharetype(String sharetype) {
        this.sharetype = sharetype == null ? null : sharetype.trim();
    }

    /**
     * @return 返还类型:0.月返,1.季返,2.年返
     */
    public String getReturntype() {
        return returntype;
    }

    /**
     * @param returntype 
	 *            返还类型:0.月返,1.季返,2.年返
     */
    public void setReturntype(String returntype) {
        this.returntype = returntype == null ? null : returntype.trim();
    }

    /**
     * @return 返还月份
     */
    public Integer getReturnmonthnum() {
        return returnmonthnum;
    }

    /**
     * @param returnmonthnum 
	 *            返还月份
     */
    public void setReturnmonthnum(Integer returnmonthnum) {
        this.returnmonthnum = returnmonthnum;
    }

    /**
     * @return 返还要求:0.无要求,1.销售达成
     */
    public String getReturnrequire() {
        return returnrequire;
    }

    /**
     * @param returnrequire 
	 *            返还要求:0.无要求,1.销售达成
     */
    public void setReturnrequire(String returnrequire) {
        this.returnrequire = returnrequire == null ? null : returnrequire.trim();
    }

    /**
     * @return 对应费用科目
     */
    public String getSubjectexpenses() {
        return subjectexpenses;
    }

    /**
     * @param subjectexpenses 
	 *            对应费用科目
     */
    public void setSubjectexpenses(String subjectexpenses) {
        this.subjectexpenses = subjectexpenses == null ? null : subjectexpenses.trim();
    }

    /**
     * @return 状态
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
     * @return 制单人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            制单人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 制单人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            制单人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 制单时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最后修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最后修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 最后修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            最后修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 审核人编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核人名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核人名称
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * @return 审核时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAudittime() {
        return audittime;
    }

    /**
     * @param audittime 
	 *            审核时间
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * @return 中止人编号
     */
    public String getStopuserid() {
        return stopuserid;
    }

    /**
     * @param stopuserid 
	 *            中止人编号
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid == null ? null : stopuserid.trim();
    }

    /**
     * @return 中止人名称
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * @param stopusername 
	 *            中止人名称
     */
    public void setStopusername(String stopusername) {
        this.stopusername = stopusername == null ? null : stopusername.trim();
    }

    /**
     * @return 中止时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getStoptime() {
        return stoptime;
    }

    /**
     * @param stoptime 
	 *            中止时间
     */
    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * @return 关闭时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            关闭时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 打印次数
     */

    public Integer getPrinttimes() {
        return printtimes;
    }

    /**
     * @param printtimes 
	 *            打印次数
     */
    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    /**
     * @return 打印时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPrintdatetime() {
        return printdatetime;
    }

    /**
     * @param printdatetime 
	 *            打印时间
     */
    public void setPrintdatetime(Date printdatetime) {
        this.printdatetime = printdatetime;
    }

    private  String typename;

    private  String statename;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }
}