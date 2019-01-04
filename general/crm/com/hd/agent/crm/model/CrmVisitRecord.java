package com.hd.agent.crm.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 客户拜访记录
 * @author master
 *
 */
public class CrmVisitRecord implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8054314960036293265L;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;
    /**
     * 唯一标识，防止重复上传
     */
    private String keyid;

    /**
     * 星期几编码
     */
    private Integer weekday;

    /**
     * 星期几
     */
    private String weekdayname;

    /**
     * 状态2保存3审核（已检查）4关闭
     */
    private String status;

    /**
     * 人员编号(人员档案)
     */
    private String personid;
    /**
     * 人员名称
     */
    private String personname;

    /**
     * 主管编号(人员档案)
     */
    private String leadid;
    /**
     * 主管名称
     */
    private String leadname;

    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 总店编号
     */
    private String pcustomerid;
    /**
     * 总店名称
     */
    private String pcustomername;

    /**
     * 门店图片路径
     */
    private String imgsrc;

    /**
     * 备注
     */
    private String remark;
    /**
     * gps位置
     */
    private String gps;
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
     * 客户分类
     */
    private String customersort;
    /**
     * 客户分类名称
     */
    private String customersortname;

    /**
     * 销售区域
     */
    private String salesarea;
    /**
     * 销售区域名称
     */
    private String salesareaname;

    /**
     * 销售部门
     */
    private String salesdept;
    /**
     * 销售部门名称
     */
    private String salesdeptname;

    /**
     * 是否线路内 1：是；0：否
     */
    private String isplan;

    /**
     * 客户拜访记录明细列表
     */
    private List<CrmVisitRecordDetail> crmVisitRecordDetailList;

    /**
     * @return 单据编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            单据编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate
     *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

    /**
     * @return 星期几
     */
    public Integer getWeekday() {
        return weekday;
    }

    /**
     * @param weekday
     *            星期几
     */
    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    /**
     * @return 状态2保存3审核（已检查）4关闭
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            状态2保存3审核（已检查）4关闭
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 人员编号(人员档案)
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid
     *            人员编号(人员档案)
     */
    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

    /**
     * @return 主管编号(人员档案)
     */
    public String getLeadid() {
        return leadid;
    }

    /**
     * @param leadid
     *            主管编号(人员档案)
     */
    public void setLeadid(String leadid) {
        this.leadid = leadid == null ? null : leadid.trim();
    }

    /**
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid
     *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 总店编号
     */
    public String getPcustomerid() {
        return pcustomerid;
    }

    /**
     * @param pcustomerid
     *            总店编号
     */
    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid == null ? null : pcustomerid.trim();
    }

    /**
     * @return 门店图片路径
     */
    public String getImgsrc() {
        return imgsrc;
    }

    /**
     * @param imgsrc
     *            门店图片路径
     */
    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc == null ? null : imgsrc.trim();
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
     * @return 客户分类
     */
    public String getCustomersort() {
        return customersort;
    }

    /**
     * @param customersort
     *            客户分类
     */
    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    /**
     * @return 销售区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea
     *            销售区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    /**
     * @return 销售部门
     */
    public String getSalesdept() {
        return salesdept;
    }

    /**
     * @param salesdept
     *            销售部门
     */
    public void setSalesdept(String salesdept) {
        this.salesdept = salesdept == null ? null : salesdept.trim();
    }

    public List<CrmVisitRecordDetail> getCrmVisitRecordDetailList() {
        return crmVisitRecordDetailList;
    }

    public void setCrmVisitRecordDetailList(
            List<CrmVisitRecordDetail> crmVisitRecordDetailList) {
        this.crmVisitRecordDetailList = crmVisitRecordDetailList;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname == null ? null : personname.trim();
    }

    public String getLeadname() {
        return leadname;
    }

    public void setLeadname(String leadname) {
        this.leadname = leadname == null ? null : leadname.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getPcustomername() {
        return pcustomername;
    }

    public void setPcustomername(String pcustomername) {
        this.pcustomername = pcustomername == null ? null : pcustomername.trim();
    }

    public String getSalesareaname() {
        return salesareaname;
    }

    public void setSalesareaname(String salesareaname) {
        this.salesareaname = salesareaname == null ? null : salesareaname.trim();
    }

    public String getSalesdeptname() {
        return salesdeptname;
    }

    public void setSalesdeptname(String salesdeptname) {
        this.salesdeptname = salesdeptname == null ? null : salesdeptname.trim();
    }

    public String getCustomersortname() {
        return customersortname;
    }

    public void setCustomersortname(String customersortname) {
        this.customersortname = customersortname == null ? null : customersortname.trim();
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getWeekdayname() {
        return weekdayname;
    }

    public void setWeekdayname(String weekdayname) {
        this.weekdayname = weekdayname;
    }

    public String getIsplan() {
        return isplan;
    }

    public void setIsplan(String isplan) {
        this.isplan = isplan;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    private  Date phototime;

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPhototime() {
        return phototime;
    }

    public void setPhototime(Date phototime) {
        this.phototime = phototime;
    }
}