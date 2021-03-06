package com.hd.agent.storage.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Lend implements Serializable {
    /**
     * 编号
     *
     */
    private String id;

    /**
     * 业务日期
     *
     */
    private String businessdate;

    /**
     * 单据类型1借货单2还货单
     *
     */
    private String billtype;

    /**
     * 借货(还货)人类型1供应商2客户
     *
     */
    private String lendtype;

    private String lendtypename;

    public String getLendtypename() {
        return lendtypename;
    }

    public void setLendtypename(String lendtypename) {
        this.lendtypename = lendtypename;
    }


    /**
     * 借货(还货)人编号
     *
     */
    private String lendid;

    private String lendname;

    /**
     * 部门编号
     *
     */
    private String deptid;

    /**
     * 相关部门名称
     */
    private String deptname;

    /**
     * 仓库编号
     *
     */
    private String storageid;

    /**
     * 仓库名称
     */
    private String storagename;

    /**
     * 状态
     *
     */
    private String status;

    /**
     * 备注
     *
     */
    private String remark;

    /**
     * 制单人编号
     *
     */
    private String adduserid;

    /**
     * 制单人姓名
     *
     */
    private String addusername;

    /**
     * 制单人部门编号
     *
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     *
     */
    private String adddeptname;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     *
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     *
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 审核人编号
     *
     */
    private String audituserid;

    /**
     * 审核人名称
     *
     */
    private String auditusername;

    /**
     * 审核时间
     *
     */
    private Date audittime;

    /**
     * 中止人编号
     *
     */
    private String stopuserid;

    /**
     * 中止人名称
     *
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
     *
     */
    private Integer printtimes;

    /**
     * 相关其他出入库单据
     *
     */
    private String otherid;


    /**
     * 明细
     */
    private List lendDetailList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_storage_lend
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     * @return  id  编号
     *
     */
    public String getId() {
        return id;
    }

    /**
     * 编号
     * @param  id  编号
     *
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 业务日期
     * @return  businessdate  业务日期
     *
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * 业务日期
     * @param  businessdate  业务日期
     *
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

    /**
     * 单据类型1借货单2还货单
     * @return  billtype  单据类型1借货单2还货单
     *
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * 单据类型1借货单2还货单
     * @param  billtype  单据类型1借货单2还货单
     *
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * 借货(还货)人类型1供应商2客户
     * @return  lendtype  借货(还货)人类型1供应商2客户
     *
     */
    public String getLendtype() {
        return lendtype;
    }

    /**
     * 借货(还货)人类型1供应商2客户
     * @param  lendtype  借货(还货)人类型1供应商2客户
     *
     */
    public void setLendtype(String lendtype) {
        this.lendtype = lendtype == null ? null : lendtype.trim();
    }

    /**
     * 借货(还货)人编号
     * @return  lendid  借货(还货)人编号
     *
     */
    public String getLendid() {
        return lendid;
    }

    /**
     * 借货(还货)人编号
     * @param  lendid  借货(还货)人编号
     *
     */
    public void setLendid(String lendid) {
        this.lendid = lendid == null ? null : lendid.trim();
    }

    /**
     * 部门编号
     * @return  deptid  部门编号
     *
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * 部门编号
     * @param  deptid  部门编号
     *
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    /**
     * 仓库编号
     * @return  storageid  仓库编号
     *
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * 仓库编号
     * @param  storageid  仓库编号
     *
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * 状态
     * @return  status  状态
     *
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param  status  状态
     *
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 备注
     * @return  remark  备注
     *
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param  remark  备注
     *
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 制单人编号
     * @return  adduserid  制单人编号
     *
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * 制单人编号
     * @param  adduserid  制单人编号
     *
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * 制单人姓名
     * @return  addusername  制单人姓名
     *
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * 制单人姓名
     * @param  addusername  制单人姓名
     *
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * 制单人部门编号
     * @return  adddeptid  制单人部门编号
     *
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * 制单人部门编号
     * @param  adddeptid  制单人部门编号
     *
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * 制单人部门名称
     * @return  adddeptname  制单人部门名称
     *

     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * 制单人部门名称
     * @param  adddeptname  制单人部门名称
     *
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * 制单时间
     * @return  addtime  制单时间
     *
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 制单时间
     * @param  addtime  制单时间
     *
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * 最后修改人编号
     * @return  modifyuserid  最后修改人编号
     *
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * 最后修改人编号
     * @param  modifyuserid  最后修改人编号
     *
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * 最后修改人名称
     * @return  modifyusername  最后修改人名称
     *
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * 最后修改人名称
     * @param  modifyusername  最后修改人名称
     *
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * 最后修改时间
     * @return  modifytime  最后修改时间
     *
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * 最后修改时间
     * @param  modifytime  最后修改时间
     *
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * 审核人编号
     * @return  audituserid  审核人编号
     *
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * 审核人编号
     * @param  audituserid  审核人编号
     *
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * 审核人名称
     * @return  auditusername  审核人名称
     *
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * 审核人名称
     * @param  auditusername  审核人名称
     *
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * 审核时间
     * @return  audittime  审核时间
     *
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAudittime() {
        return audittime;
    }

    /**
     * 审核时间
     * @param  audittime  审核时间
     *
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * 中止人编号
     * @return  stopuserid  中止人编号
     *
     */
    public String getStopuserid() {
        return stopuserid;
    }

    /**
     * 中止人编号
     * @param  stopuserid  中止人编号
     *
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid == null ? null : stopuserid.trim();
    }

    /**
     * 中止人名称
     * @return  stopusername  中止人名称
     *
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * 中止人名称
     * @param  stopusername  中止人名称
     *
     */
    public void setStopusername(String stopusername) {
        this.stopusername = stopusername == null ? null : stopusername.trim();
    }

    /**
     * 中止时间
     * @return  stoptime  中止时间
     *
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getStoptime() {
        return stoptime;
    }

    /**
     * 中止时间
     * @param  stoptime  中止时间
     *
     */
    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * 关闭时间
     * @return  closetime  关闭时间
     *
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getClosetime() {
        return closetime;
    }

    /**
     * 关闭时间
     * @param  closetime  关闭时间
     *
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * 打印次数
     * @return  printtimes  打印次数
     *
     */
    public Integer getPrinttimes() {
        return printtimes;
    }

    /**
     * 打印次数
     * @param  printtimes  打印次数
     *
     */
    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    /**
     * 相关其他出入库单据
     * @return  otherid  相关其他出入库单据
     *
     */
    public String getOtherid() {
        return otherid;
    }

    /**
     * 相关其他出入库单据
     * @param  otherid  相关其他出入库单据
     *
     */
    public void setOtherid(String otherid) {
        this.otherid = otherid == null ? null : otherid.trim();
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }


    public String getLendname() {
        return lendname;
    }

    public void setLendname(String lendname) {
        this.lendname = lendname;
    }
    public List getLendDetailList() {
        return lendDetailList;
    }

    public void setLendDetailList(List lendDetailList) {
        this.lendDetailList = lendDetailList;
    }
}