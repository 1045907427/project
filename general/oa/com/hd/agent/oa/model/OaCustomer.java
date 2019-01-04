package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OaCustomer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8819943636146402971L;

	/**
     * 编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态
     */
    private String status;

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
     * 客户编号
     */
    private String customerid;

    /**
     * 客户名称
     */
    private String customername;

    /**
     * 简称
     */
    private String shortname;

    /**
     * 助词符
     */
    private String shortcode;

    /**
     * ABC等级
     */
    private String abclevel;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 传真
     */
    private String faxno;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 网址
     */
    private String website;

    /**
     * 法人代表
     */
    private String person;

    /**
     * 法人代表电话
     */
    private String personmobile;

    /**
     * 法人身份证号码
     */
    private String personcard;

    /**
     * 公司属性
     */
    private String nature;

    /**
     * 注册资金
     */
    private BigDecimal fund;

    /**
     * 客户开户日期
     */
    private String setupdate;

    /**
     * 员工人数
     */
    private String staffnum;

    /**
     * 年营业额
     */
    private BigDecimal turnoveryear;

    /**
     * 初次业务日期
     */
    private String firstbusinessdate;

    /**
     * 所属区域
     */
    private String salesarea;

    /**
     * 所属分类
     */
    private String customersort;

    /**
     * 对方联系人
     */
    private String contact;

    /**
     * 地址
     */
    private String address;

    /**
     * 促销分类
     */
    private String promotionsort;

    /**
     * 邮编
     */
    private String zip;

    /**
     * 税号
     */
    private String taxno;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 开户账号
     */
    private String cardno;

    /**
     * 开户名
     */
    private String caraccount;

    /**
     * 门店面积
     */
    private BigDecimal storearea;

    /**
     * 是否连锁
     */
    private String ischain;

    /**
     * 县级市
     */
    private String countylevel;

    /**
     * 乡镇
     */
    private String villagetown;

    /**
     * 信用额度
     */
    private BigDecimal credit;

    /**
     * 信用等级
     */
    private String creditrating;

    /**
     * 对账日期
     */
    private String reconciliationdate;

    /**
     * 开票日期
     */
    private String billingdate;

    /**
     * 到款日期
     */
    private String arrivalamountdate;

    /**
     * 票种
     */
    private String tickettype;

    /**
     * 信用期限
     */
    private String creditdate;

    /**
     * 月销售指标
     */
    private BigDecimal salesmonth;

    /**
     * 目标销售
     */
    private BigDecimal targetsales;

    /**
     * 年返
     */
    private BigDecimal yearback;

    /**
     * 月返
     */
    private BigDecimal monthback;

    /**
     * 配送费
     */
    private BigDecimal dispatchingamount;

    /**
     * 六节一庆
     */
    private BigDecimal sixone;

    /**
     * 是否结算
     */
    private String settlement;

    /**
     * 结算方式
     */
    private String settletype;

    /**
     * 每月结算日
     */
    private String settleday;

    /**
     * 支付方式
     */
    private String paytype;

    /**
     * 是否现款
     */
    private String iscash;

    /**
     * 是否账期
     */
    private String islongterm;

    /**
     * 是否开票
     */
    private String billing;

    /**
     * 应收依据
     */
    private String billtype;

    /**
     * 超账期控制1是0否
     */
    private String overcontrol;

    /**
     * 超账期宽限天数
     */
    private Integer overgracedate;

    /**
     * 核销方式1开票2抽单3开票抽单
     */
    private String canceltype;

    /**
     * 默认销售部门
     */
    private String salesdeptid;

    /**
     * 默认销售部门名称
     */
    private String salesdeptname;

    /**
     * 默认业务员
     */
    private String salesuserid;

    /**
     * 默认业务员名称
     */
    private String salesusername;

    /**
     * 默认价格套
     */
    private String pricesort;

    /**
     * 默认理货员
     */
    private String tallyuserid;

    /**
     * 默认理货员名称
     */
    private String tallyusername;

    /**
     * 默认销售内勤
     */
    private String indoorstaff;

    /**
     * 收款人
     */
    private String payeeid;

    /**
     * 对账人
     */
    private String checker;

    /**
     * 对账人电话
     */
    private String checkmobile;

    /**
     * 对账人邮箱
     */
    private String checkemail;

    /**
     * 付款人
     */
    private String payer;

    /**
     * 付款人电话
     */
    private String payermobile;

    /**
     * 付款人邮箱
     */
    private String payeremail;

    /**
     * 店长
     */
    private String shopmanager;

    /**
     * 店长联系电话
     */
    private String shopmanagermobile;

    /**
     * 收货人
     */
    private String gsreceipter;

    /**
     * 收货人联系电话
     */
    private String gsreceiptermobile;

    /**
     * 计划毛利率
     */
    private BigDecimal margin;

    /**
     * 自定义信息1
     */
    private String field01;

    /**
     * 自定义信息2
     */
    private String field02;

    /**
     * 自定义信息3
     */
    private String field03;

    /**
     * 自定义信息4
     */
    private String field04;

    /**
     * 自定义信息5
     */
    private String field05;

    /**
     * 自定义信息6
     */
    private String field06;

    /**
     * 自定义信息7
     */
    private String field07;

    /**
     * 自定义信息8
     */
    private String field08;

    /**
     * 自定义信息9
     */
    private String field09;

    /**
     * 自定义信息10
     */
    private String field10;

    /**
     * 自定义信息11
     */
    private String field11;

    /**
     * 自定义信息12
     */
    private String field12;

    /**
     * 是否门店0总店1门店
     */
    private String islast;

    /**
     * 客户要求
     */
    private String demand;

    /**
     * 谈判结果
     */
    private String talkresult;

    /**
     * 反馈结果
     */
    private String feedback;

    /**
     * 合同期限
     */
    private String pactdeadline;

    /**
     * 第一步骤审核人名称
     */
    private String auditname1;

    /**
     * 第一步骤审核时间
     */
    private String auditdate1;

    /**
     * 第2步骤审核人名称
     */
    private String auditname2;

    /**
     * 第2步骤审核时间
     */
    private String auditdate2;

    /**
     * 第3步骤审核人名称
     */
    private String auditname3;

    /**
     * 第3步骤审核时间
     */
    private String auditdate3;

    /**
     * 第4步骤审核人名称
     */
    private String auditname4;

    /**
     * 第4步骤审核时间
     */
    private String auditdate4;

    /**
     * 第5步骤审核人名称
     */
    private String auditname5;

    /**
     * 第5步骤审核时间
     */
    private String auditdate5;

    /**
     * 第6步骤审核人名称
     */
    private String auditname6;

    /**
     * 第6步骤审核时间
     */
    private String auditdate6;

    /**
     * 第7步骤审核人名称
     */
    private String auditname7;

    /**
     * 第7步骤审核时间
     */
    private String auditdate7;

    /**
     * 第8步骤审核人名称
     */
    private String auditname8;

    /**
     * 第8步骤审核时间
     */
    private String auditdate8;

    /**
     * 上级客户
     */
    private String pcustomerid;

    /**
     * 营业执照号
     */
    private String licenseno;

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
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
     * @return 客户名称
     */
    public String getCustomername() {
        return customername;
    }

    /**
     * @param customername 
	 *            客户名称
     */
    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    /**
     * @return 简称
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * @param shortname 
	 *            简称
     */
    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }

    /**
     * @return 助词符
     */
    public String getShortcode() {
        return shortcode;
    }

    /**
     * @param shortcode 
	 *            助词符
     */
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode == null ? null : shortcode.trim();
    }

    /**
     * @return ABC等级
     */
    public String getAbclevel() {
        return abclevel;
    }

    /**
     * @param abclevel 
	 *            ABC等级
     */
    public void setAbclevel(String abclevel) {
        this.abclevel = abclevel == null ? null : abclevel.trim();
    }

    /**
     * @return 电话
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone 
	 *            电话
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * @return 传真
     */
    public String getFaxno() {
        return faxno;
    }

    /**
     * @param faxno 
	 *            传真
     */
    public void setFaxno(String faxno) {
        this.faxno = faxno == null ? null : faxno.trim();
    }

    /**
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 
	 *            邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile 
	 *            手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return 网址
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website 
	 *            网址
     */
    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    /**
     * @return 法人代表
     */
    public String getPerson() {
        return person;
    }

    /**
     * @param person 
	 *            法人代表
     */
    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }

    /**
     * @return 法人代表电话
     */
    public String getPersonmobile() {
        return personmobile;
    }

    /**
     * @param personmobile 
	 *            法人代表电话
     */
    public void setPersonmobile(String personmobile) {
        this.personmobile = personmobile == null ? null : personmobile.trim();
    }

    /**
     * @return 法人身份证号码
     */
    public String getPersoncard() {
        return personcard;
    }

    /**
     * @param personcard 
	 *            法人身份证号码
     */
    public void setPersoncard(String personcard) {
        this.personcard = personcard == null ? null : personcard.trim();
    }

    /**
     * @return 公司属性
     */
    public String getNature() {
        return nature;
    }

    /**
     * @param nature 
	 *            公司属性
     */
    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    /**
     * @return 注册资金
     */
    public BigDecimal getFund() {
        return fund;
    }

    /**
     * @param fund 
	 *            注册资金
     */
    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    /**
     * @return 客户开户日期
     */
    public String getSetupdate() {
        return setupdate;
    }

    /**
     * @param setupdate 
	 *            客户开户日期
     */
    public void setSetupdate(String setupdate) {
        this.setupdate = setupdate == null ? null : setupdate.trim();
    }

    /**
     * @return 员工人数
     */
    public String getStaffnum() {
        return staffnum;
    }

    /**
     * @param staffnum 
	 *            员工人数
     */
    public void setStaffnum(String staffnum) {
        this.staffnum = staffnum == null ? null : staffnum.trim();
    }

    /**
     * @return 年营业额
     */
    public BigDecimal getTurnoveryear() {
        return turnoveryear;
    }

    /**
     * @param turnoveryear 
	 *            年营业额
     */
    public void setTurnoveryear(BigDecimal turnoveryear) {
        this.turnoveryear = turnoveryear;
    }

    /**
     * @return 初次业务日期
     */
    public String getFirstbusinessdate() {
        return firstbusinessdate;
    }

    /**
     * @param firstbusinessdate 
	 *            初次业务日期
     */
    public void setFirstbusinessdate(String firstbusinessdate) {
        this.firstbusinessdate = firstbusinessdate == null ? null : firstbusinessdate.trim();
    }

    /**
     * @return 所属区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea 
	 *            所属区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    /**
     * @return 所属分类
     */
    public String getCustomersort() {
        return customersort;
    }

    /**
     * @param customersort 
	 *            所属分类
     */
    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    /**
     * @return 对方联系人
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact 
	 *            对方联系人
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * @return 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 
	 *            地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return 促销分类
     */
    public String getPromotionsort() {
        return promotionsort;
    }

    /**
     * @param promotionsort 
	 *            促销分类
     */
    public void setPromotionsort(String promotionsort) {
        this.promotionsort = promotionsort == null ? null : promotionsort.trim();
    }

    /**
     * @return 邮编
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip 
	 *            邮编
     */
    public void setZip(String zip) {
        this.zip = zip == null ? null : zip.trim();
    }

    /**
     * @return 税号
     */
    public String getTaxno() {
        return taxno;
    }

    /**
     * @param taxno 
	 *            税号
     */
    public void setTaxno(String taxno) {
        this.taxno = taxno == null ? null : taxno.trim();
    }

    /**
     * @return 开户银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * @param bank 
	 *            开户银行
     */
    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    /**
     * @return 开户账号
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno 
	 *            开户账号
     */
    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    /**
     * @return 开户名
     */
    public String getCaraccount() {
        return caraccount;
    }

    /**
     * @param caraccount 
	 *            开户名
     */
    public void setCaraccount(String caraccount) {
        this.caraccount = caraccount == null ? null : caraccount.trim();
    }

    /**
     * @return 门店面积
     */
    public BigDecimal getStorearea() {
        return storearea;
    }

    /**
     * @param storearea 
	 *            门店面积
     */
    public void setStorearea(BigDecimal storearea) {
        this.storearea = storearea;
    }

    /**
     * @return 是否连锁
     */
    public String getIschain() {
        return ischain;
    }

    /**
     * @param ischain 
	 *            是否连锁
     */
    public void setIschain(String ischain) {
        this.ischain = ischain == null ? null : ischain.trim();
    }

    /**
     * @return 县级市
     */
    public String getCountylevel() {
        return countylevel;
    }

    /**
     * @param countylevel 
	 *            县级市
     */
    public void setCountylevel(String countylevel) {
        this.countylevel = countylevel == null ? null : countylevel.trim();
    }

    /**
     * @return 乡镇
     */
    public String getVillagetown() {
        return villagetown;
    }

    /**
     * @param villagetown 
	 *            乡镇
     */
    public void setVillagetown(String villagetown) {
        this.villagetown = villagetown == null ? null : villagetown.trim();
    }

    /**
     * @return 信用额度
     */
    public BigDecimal getCredit() {
        return credit;
    }

    /**
     * @param credit 
	 *            信用额度
     */
    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    /**
     * @return 信用等级
     */
    public String getCreditrating() {
        return creditrating;
    }

    /**
     * @param creditrating 
	 *            信用等级
     */
    public void setCreditrating(String creditrating) {
        this.creditrating = creditrating == null ? null : creditrating.trim();
    }

    /**
     * @return 对账日期
     */
    public String getReconciliationdate() {
        return reconciliationdate;
    }

    /**
     * @param reconciliationdate 
	 *            对账日期
     */
    public void setReconciliationdate(String reconciliationdate) {
        this.reconciliationdate = reconciliationdate == null ? null : reconciliationdate.trim();
    }

    /**
     * @return 开票日期
     */
    public String getBillingdate() {
        return billingdate;
    }

    /**
     * @param billingdate 
	 *            开票日期
     */
    public void setBillingdate(String billingdate) {
        this.billingdate = billingdate == null ? null : billingdate.trim();
    }

    /**
     * @return 到款日期
     */
    public String getArrivalamountdate() {
        return arrivalamountdate;
    }

    /**
     * @param arrivalamountdate 
	 *            到款日期
     */
    public void setArrivalamountdate(String arrivalamountdate) {
        this.arrivalamountdate = arrivalamountdate == null ? null : arrivalamountdate.trim();
    }

    /**
     * @return 票种
     */
    public String getTickettype() {
        return tickettype;
    }

    /**
     * @param tickettype 
	 *            票种
     */
    public void setTickettype(String tickettype) {
        this.tickettype = tickettype == null ? null : tickettype.trim();
    }

    /**
     * @return 信用期限
     */
    public String getCreditdate() {
        return creditdate;
    }

    /**
     * @param creditdate 
	 *            信用期限
     */
    public void setCreditdate(String creditdate) {
        this.creditdate = creditdate == null ? null : creditdate.trim();
    }

    /**
     * @return 月销售指标
     */
    public BigDecimal getSalesmonth() {
        return salesmonth;
    }

    /**
     * @param salesmonth 
	 *            月销售指标
     */
    public void setSalesmonth(BigDecimal salesmonth) {
        this.salesmonth = salesmonth;
    }

    /**
     * @return 目标销售
     */
    public BigDecimal getTargetsales() {
        return targetsales;
    }

    /**
     * @param targetsales 
	 *            目标销售
     */
    public void setTargetsales(BigDecimal targetsales) {
        this.targetsales = targetsales;
    }

    /**
     * @return 年返
     */
    public BigDecimal getYearback() {
        return yearback;
    }

    /**
     * @param yearback 
	 *            年返
     */
    public void setYearback(BigDecimal yearback) {
        this.yearback = yearback;
    }

    /**
     * @return 月返
     */
    public BigDecimal getMonthback() {
        return monthback;
    }

    /**
     * @param monthback 
	 *            月返
     */
    public void setMonthback(BigDecimal monthback) {
        this.monthback = monthback;
    }

    /**
     * @return 配送费
     */
    public BigDecimal getDispatchingamount() {
        return dispatchingamount;
    }

    /**
     * @param dispatchingamount 
	 *            配送费
     */
    public void setDispatchingamount(BigDecimal dispatchingamount) {
        this.dispatchingamount = dispatchingamount;
    }

    /**
     * @return 六节一庆
     */
    public BigDecimal getSixone() {
        return sixone;
    }

    /**
     * @param sixone 
	 *            六节一庆
     */
    public void setSixone(BigDecimal sixone) {
        this.sixone = sixone;
    }

    /**
     * @return 是否结算
     */
    public String getSettlement() {
        return settlement;
    }

    /**
     * @param settlement 
	 *            是否结算
     */
    public void setSettlement(String settlement) {
        this.settlement = settlement == null ? null : settlement.trim();
    }

    /**
     * @return 结算方式
     */
    public String getSettletype() {
        return settletype;
    }

    /**
     * @param settletype 
	 *            结算方式
     */
    public void setSettletype(String settletype) {
        this.settletype = settletype == null ? null : settletype.trim();
    }

    /**
     * @return 每月结算日
     */
    public String getSettleday() {
        return settleday;
    }

    /**
     * @param settleday 
	 *            每月结算日
     */
    public void setSettleday(String settleday) {
        this.settleday = settleday == null ? null : settleday.trim();
    }

    /**
     * @return 支付方式
     */
    public String getPaytype() {
        return paytype;
    }

    /**
     * @param paytype 
	 *            支付方式
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    /**
     * @return 是否现款
     */
    public String getIscash() {
        return iscash;
    }

    /**
     * @param iscash 
	 *            是否现款
     */
    public void setIscash(String iscash) {
        this.iscash = iscash == null ? null : iscash.trim();
    }

    /**
     * @return 是否账期
     */
    public String getIslongterm() {
        return islongterm;
    }

    /**
     * @param islongterm 
	 *            是否账期
     */
    public void setIslongterm(String islongterm) {
        this.islongterm = islongterm == null ? null : islongterm.trim();
    }

    /**
     * @return 是否开票
     */
    public String getBilling() {
        return billing;
    }

    /**
     * @param billing 
	 *            是否开票
     */
    public void setBilling(String billing) {
        this.billing = billing == null ? null : billing.trim();
    }

    /**
     * @return 应收依据
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            应收依据
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * @return 超账期控制1是0否
     */
    public String getOvercontrol() {
        return overcontrol;
    }

    /**
     * @param overcontrol 
	 *            超账期控制1是0否
     */
    public void setOvercontrol(String overcontrol) {
        this.overcontrol = overcontrol == null ? null : overcontrol.trim();
    }

    /**
     * @return 超账期宽限天数
     */
    public Integer getOvergracedate() {
        return overgracedate;
    }

    /**
     * @param overgracedate 
	 *            超账期宽限天数
     */
    public void setOvergracedate(Integer overgracedate) {
        this.overgracedate = overgracedate;
    }

    /**
     * @return 核销方式1开票2抽单3开票抽单
     */
    public String getCanceltype() {
        return canceltype;
    }

    /**
     * @param canceltype 
	 *            核销方式1开票2抽单3开票抽单
     */
    public void setCanceltype(String canceltype) {
        this.canceltype = canceltype == null ? null : canceltype.trim();
    }

    /**
     * @return 默认销售部门
     */
    public String getSalesdeptid() {
        return salesdeptid;
    }

    /**
     * @param salesdeptid 
	 *            默认销售部门
     */
    public void setSalesdeptid(String salesdeptid) {
        this.salesdeptid = salesdeptid == null ? null : salesdeptid.trim();
    }

    /**
     * @return 默认销售部门名称
     */
    public String getSalesdeptname() {
        return salesdeptname;
    }

    /**
     * @param salesdeptname 
	 *            默认销售部门名称
     */
    public void setSalesdeptname(String salesdeptname) {
        this.salesdeptname = salesdeptname == null ? null : salesdeptname.trim();
    }

    /**
     * @return 默认业务员
     */
    public String getSalesuserid() {
        return salesuserid;
    }

    /**
     * @param salesuserid 
	 *            默认业务员
     */
    public void setSalesuserid(String salesuserid) {
        this.salesuserid = salesuserid == null ? null : salesuserid.trim();
    }

    /**
     * @return 默认业务员名称
     */
    public String getSalesusername() {
        return salesusername;
    }

    /**
     * @param salesusername 
	 *            默认业务员名称
     */
    public void setSalesusername(String salesusername) {
        this.salesusername = salesusername == null ? null : salesusername.trim();
    }

    /**
     * @return 默认价格套
     */
    public String getPricesort() {
        return pricesort;
    }

    /**
     * @param pricesort 
	 *            默认价格套
     */
    public void setPricesort(String pricesort) {
        this.pricesort = pricesort == null ? null : pricesort.trim();
    }

    /**
     * @return 默认理货员
     */
    public String getTallyuserid() {
        return tallyuserid;
    }

    /**
     * @param tallyuserid 
	 *            默认理货员
     */
    public void setTallyuserid(String tallyuserid) {
        this.tallyuserid = tallyuserid == null ? null : tallyuserid.trim();
    }

    /**
     * @return 默认理货员名称
     */
    public String getTallyusername() {
        return tallyusername;
    }

    /**
     * @param tallyusername 
	 *            默认理货员名称
     */
    public void setTallyusername(String tallyusername) {
        this.tallyusername = tallyusername == null ? null : tallyusername.trim();
    }

    /**
     * @return 默认销售内勤
     */
    public String getIndoorstaff() {
        return indoorstaff;
    }

    /**
     * @param indoorstaff 
	 *            默认销售内勤
     */
    public void setIndoorstaff(String indoorstaff) {
        this.indoorstaff = indoorstaff == null ? null : indoorstaff.trim();
    }

    /**
     * @return 收款人
     */
    public String getPayeeid() {
        return payeeid;
    }

    /**
     * @param payeeid 
	 *            收款人
     */
    public void setPayeeid(String payeeid) {
        this.payeeid = payeeid == null ? null : payeeid.trim();
    }

    /**
     * @return 对账人
     */
    public String getChecker() {
        return checker;
    }

    /**
     * @param checker 
	 *            对账人
     */
    public void setChecker(String checker) {
        this.checker = checker == null ? null : checker.trim();
    }

    /**
     * @return 对账人电话
     */
    public String getCheckmobile() {
        return checkmobile;
    }

    /**
     * @param checkmobile 
	 *            对账人电话
     */
    public void setCheckmobile(String checkmobile) {
        this.checkmobile = checkmobile == null ? null : checkmobile.trim();
    }

    /**
     * @return 对账人邮箱
     */
    public String getCheckemail() {
        return checkemail;
    }

    /**
     * @param checkemail 
	 *            对账人邮箱
     */
    public void setCheckemail(String checkemail) {
        this.checkemail = checkemail == null ? null : checkemail.trim();
    }

    /**
     * @return 付款人
     */
    public String getPayer() {
        return payer;
    }

    /**
     * @param payer 
	 *            付款人
     */
    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    /**
     * @return 付款人电话
     */
    public String getPayermobile() {
        return payermobile;
    }

    /**
     * @param payermobile 
	 *            付款人电话
     */
    public void setPayermobile(String payermobile) {
        this.payermobile = payermobile == null ? null : payermobile.trim();
    }

    /**
     * @return 付款人邮箱
     */
    public String getPayeremail() {
        return payeremail;
    }

    /**
     * @param payeremail 
	 *            付款人邮箱
     */
    public void setPayeremail(String payeremail) {
        this.payeremail = payeremail == null ? null : payeremail.trim();
    }

    /**
     * @return 店长
     */
    public String getShopmanager() {
        return shopmanager;
    }

    /**
     * @param shopmanager 
	 *            店长
     */
    public void setShopmanager(String shopmanager) {
        this.shopmanager = shopmanager == null ? null : shopmanager.trim();
    }

    /**
     * @return 店长联系电话
     */
    public String getShopmanagermobile() {
        return shopmanagermobile;
    }

    /**
     * @param shopmanagermobile 
	 *            店长联系电话
     */
    public void setShopmanagermobile(String shopmanagermobile) {
        this.shopmanagermobile = shopmanagermobile == null ? null : shopmanagermobile.trim();
    }

    /**
     * @return 收货人
     */
    public String getGsreceipter() {
        return gsreceipter;
    }

    /**
     * @param gsreceipter 
	 *            收货人
     */
    public void setGsreceipter(String gsreceipter) {
        this.gsreceipter = gsreceipter == null ? null : gsreceipter.trim();
    }

    /**
     * @return 收货人联系电话
     */
    public String getGsreceiptermobile() {
        return gsreceiptermobile;
    }

    /**
     * @param gsreceiptermobile 
	 *            收货人联系电话
     */
    public void setGsreceiptermobile(String gsreceiptermobile) {
        this.gsreceiptermobile = gsreceiptermobile == null ? null : gsreceiptermobile.trim();
    }

    /**
     * @return 计划毛利率
     */
    public BigDecimal getMargin() {
        return margin;
    }

    /**
     * @param margin 
	 *            计划毛利率
     */
    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    /**
     * @return 自定义信息1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01 
	 *            自定义信息1
     */
    public void setField01(String field01) {
        this.field01 = field01 == null ? null : field01.trim();
    }

    /**
     * @return 自定义信息2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02 
	 *            自定义信息2
     */
    public void setField02(String field02) {
        this.field02 = field02 == null ? null : field02.trim();
    }

    /**
     * @return 自定义信息3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03 
	 *            自定义信息3
     */
    public void setField03(String field03) {
        this.field03 = field03 == null ? null : field03.trim();
    }

    /**
     * @return 自定义信息4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04 
	 *            自定义信息4
     */
    public void setField04(String field04) {
        this.field04 = field04 == null ? null : field04.trim();
    }

    /**
     * @return 自定义信息5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05 
	 *            自定义信息5
     */
    public void setField05(String field05) {
        this.field05 = field05 == null ? null : field05.trim();
    }

    /**
     * @return 自定义信息6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06 
	 *            自定义信息6
     */
    public void setField06(String field06) {
        this.field06 = field06 == null ? null : field06.trim();
    }

    /**
     * @return 自定义信息7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07 
	 *            自定义信息7
     */
    public void setField07(String field07) {
        this.field07 = field07 == null ? null : field07.trim();
    }

    /**
     * @return 自定义信息8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08 
	 *            自定义信息8
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

    /**
     * @return 自定义信息9
     */
    public String getField09() {
        return field09;
    }

    /**
     * @param field09 
	 *            自定义信息9
     */
    public void setField09(String field09) {
        this.field09 = field09 == null ? null : field09.trim();
    }

    /**
     * @return 自定义信息10
     */
    public String getField10() {
        return field10;
    }

    /**
     * @param field10 
	 *            自定义信息10
     */
    public void setField10(String field10) {
        this.field10 = field10 == null ? null : field10.trim();
    }

    /**
     * @return 自定义信息11
     */
    public String getField11() {
        return field11;
    }

    /**
     * @param field11 
	 *            自定义信息11
     */
    public void setField11(String field11) {
        this.field11 = field11 == null ? null : field11.trim();
    }

    /**
     * @return 自定义信息12
     */
    public String getField12() {
        return field12;
    }

    /**
     * @param field12 
	 *            自定义信息12
     */
    public void setField12(String field12) {
        this.field12 = field12 == null ? null : field12.trim();
    }

    /**
     * @return 是否门店0总店1门店
     */
    public String getIslast() {
        return islast;
    }

    /**
     * @param islast 
	 *            是否门店0总店1门店
     */
    public void setIslast(String islast) {
        this.islast = islast == null ? null : islast.trim();
    }

    /**
     * @return 客户要求
     */
    public String getDemand() {
        return demand;
    }

    /**
     * @param demand 
	 *            客户要求
     */
    public void setDemand(String demand) {
        this.demand = demand == null ? null : demand.trim();
    }

    /**
     * @return 谈判结果
     */
    public String getTalkresult() {
        return talkresult;
    }

    /**
     * @param talkresult 
	 *            谈判结果
     */
    public void setTalkresult(String talkresult) {
        this.talkresult = talkresult == null ? null : talkresult.trim();
    }

    /**
     * @return 反馈结果
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback 
	 *            反馈结果
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    /**
     * @return 合同期限
     */
    public String getPactdeadline() {
        return pactdeadline;
    }

    /**
     * @param pactdeadline 
	 *            合同期限
     */
    public void setPactdeadline(String pactdeadline) {
        this.pactdeadline = pactdeadline == null ? null : pactdeadline.trim();
    }

    /**
     * @return 第一步骤审核人名称
     */
    public String getAuditname1() {
        return auditname1;
    }

    /**
     * @param auditname1 
	 *            第一步骤审核人名称
     */
    public void setAuditname1(String auditname1) {
        this.auditname1 = auditname1 == null ? null : auditname1.trim();
    }

    /**
     * @return 第一步骤审核时间
     */
    public String getAuditdate1() {
        return auditdate1;
    }

    /**
     * @param auditdate1 
	 *            第一步骤审核时间
     */
    public void setAuditdate1(String auditdate1) {
        this.auditdate1 = auditdate1 == null ? null : auditdate1.trim();
    }

    /**
     * @return 第2步骤审核人名称
     */
    public String getAuditname2() {
        return auditname2;
    }

    /**
     * @param auditname2 
	 *            第2步骤审核人名称
     */
    public void setAuditname2(String auditname2) {
        this.auditname2 = auditname2 == null ? null : auditname2.trim();
    }

    /**
     * @return 第2步骤审核时间
     */
    public String getAuditdate2() {
        return auditdate2;
    }

    /**
     * @param auditdate2 
	 *            第2步骤审核时间
     */
    public void setAuditdate2(String auditdate2) {
        this.auditdate2 = auditdate2 == null ? null : auditdate2.trim();
    }

    /**
     * @return 第3步骤审核人名称
     */
    public String getAuditname3() {
        return auditname3;
    }

    /**
     * @param auditname3 
	 *            第3步骤审核人名称
     */
    public void setAuditname3(String auditname3) {
        this.auditname3 = auditname3 == null ? null : auditname3.trim();
    }

    /**
     * @return 第3步骤审核时间
     */
    public String getAuditdate3() {
        return auditdate3;
    }

    /**
     * @param auditdate3 
	 *            第3步骤审核时间
     */
    public void setAuditdate3(String auditdate3) {
        this.auditdate3 = auditdate3 == null ? null : auditdate3.trim();
    }

    /**
     * @return 第4步骤审核人名称
     */
    public String getAuditname4() {
        return auditname4;
    }

    /**
     * @param auditname4 
	 *            第4步骤审核人名称
     */
    public void setAuditname4(String auditname4) {
        this.auditname4 = auditname4 == null ? null : auditname4.trim();
    }

    /**
     * @return 第4步骤审核时间
     */
    public String getAuditdate4() {
        return auditdate4;
    }

    /**
     * @param auditdate4 
	 *            第4步骤审核时间
     */
    public void setAuditdate4(String auditdate4) {
        this.auditdate4 = auditdate4 == null ? null : auditdate4.trim();
    }

    /**
     * @return 第5步骤审核人名称
     */
    public String getAuditname5() {
        return auditname5;
    }

    /**
     * @param auditname5 
	 *            第5步骤审核人名称
     */
    public void setAuditname5(String auditname5) {
        this.auditname5 = auditname5 == null ? null : auditname5.trim();
    }

    /**
     * @return 第5步骤审核时间
     */
    public String getAuditdate5() {
        return auditdate5;
    }

    /**
     * @param auditdate5 
	 *            第5步骤审核时间
     */
    public void setAuditdate5(String auditdate5) {
        this.auditdate5 = auditdate5 == null ? null : auditdate5.trim();
    }

    /**
     * @return 第6步骤审核人名称
     */
    public String getAuditname6() {
        return auditname6;
    }

    /**
     * @param auditname6 
	 *            第6步骤审核人名称
     */
    public void setAuditname6(String auditname6) {
        this.auditname6 = auditname6 == null ? null : auditname6.trim();
    }

    /**
     * @return 第6步骤审核时间
     */
    public String getAuditdate6() {
        return auditdate6;
    }

    /**
     * @param auditdate6 
	 *            第6步骤审核时间
     */
    public void setAuditdate6(String auditdate6) {
        this.auditdate6 = auditdate6 == null ? null : auditdate6.trim();
    }

    /**
     * @return 第7步骤审核人名称
     */
    public String getAuditname7() {
        return auditname7;
    }

    /**
     * @param auditname7 
	 *            第7步骤审核人名称
     */
    public void setAuditname7(String auditname7) {
        this.auditname7 = auditname7 == null ? null : auditname7.trim();
    }

    /**
     * @return 第7步骤审核时间
     */
    public String getAuditdate7() {
        return auditdate7;
    }

    /**
     * @param auditdate7 
	 *            第7步骤审核时间
     */
    public void setAuditdate7(String auditdate7) {
        this.auditdate7 = auditdate7 == null ? null : auditdate7.trim();
    }

    /**
     * @return 第8步骤审核人名称
     */
    public String getAuditname8() {
        return auditname8;
    }

    /**
     * @param auditname8 
	 *            第8步骤审核人名称
     */
    public void setAuditname8(String auditname8) {
        this.auditname8 = auditname8 == null ? null : auditname8.trim();
    }

    /**
     * @return 第8步骤审核时间
     */
    public String getAuditdate8() {
        return auditdate8;
    }

    /**
     * @param auditdate8 
	 *            第8步骤审核时间
     */
    public void setAuditdate8(String auditdate8) {
        this.auditdate8 = auditdate8 == null ? null : auditdate8.trim();
    }

    public String getPcustomerid() {
        return pcustomerid;
    }

    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid;
    }

    public String getLicenseno() {
        return licenseno;
    }

    public void setLicenseno(String licenseno) {
        this.licenseno = licenseno;
    }
}