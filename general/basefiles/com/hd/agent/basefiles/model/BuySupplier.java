/**
 * @(#)BuySupplier.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-17 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class BuySupplier implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -685159536177488344L;

	 /**
     * 编码
     */
    private String id;
    
    /**
     * 原始编码
     */
    private String oldid;

    /**
     * 名称(全称)
     */
    private String name;
    
    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 状态4新增3暂存2保存1启用0禁用
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String statename;

    /**
     * 备注
     */
    private String remark;

    /**
     * 简称
     */
    private String shortname;

    /**
     * 助记符
     */
    private String spell;

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
     * 法人代表身份证
     */
    private String personcard;

    /**
     * 企业性质
     */
    private String nature;

    /**
     * 注册资金
     */
    private String fund;

    /**
     * 成立日期
     */
    private String setupdate;

    /**
     * 员工人数
     */
    private String staffnum;

    /**
     * 年产值
     */
    private String turnoveryear;

    /**
     * 初次业务日期
     */
    private String firstbusinessdate;

    /**
     * 所属采购区域
     */
    private String buyarea;
    
    /**
     * 所属区域名称
     */
    private String buyareaname;

    /**
     * 所属供应商分类
     */
    private String suppliersort;
    
    /**
     * 所属供应商分类名称
     */
    private String suppliersortname;

    /**
     * 地址
     */
    private String address;

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
     * 信用额度
     */
    private BigDecimal credit;

    /**
     * 信用日期
     */
    private String creditdate;

    /**
     * 月销售指标
     */
    private BigDecimal salesmonth;

    /**
     * 结算方式
     */
    private String settletype;
    
    /**
     * 结算方式名称
     */
    private String settletypename;

    /**
     * 每月结算日
     */
    private String settleday;

    /**
     * 资金占用额度
     */
    private BigDecimal ownlimit;

    /**
     * 支付方式
     */
    private String paytype;

    /**
     * 默认采购部门(来自部门档案)
     */
    private String buydeptid;
    
    /**
     * 采购部门名称
     */
    private String buydeptname;

    /**
     * 默认采购员（来自人员档案）
     */
    private String buyuserid;
    
    /**
     * 采购员名称
     */
    private String buyusername;

    /**
     * 采购员联系电话
     */
    private String buyusermobile;

    /**
     * 默认仓库(来自仓库档案)
     */
    private String storageid;
    
    /**
     * 默认仓库名称
     */
    private String storagename;

    /**
     * 累计采购金额
     */
    private BigDecimal buysum;

    /**
     * 累计付款金额
     */
    private BigDecimal paysum;

    /**
     * 应付金额
     */
    private BigDecimal payable;

    /**
     * 其他应收金额
     */
    private BigDecimal otherduefromsum;

    /**
     * 本年累计采购金额
     */
    private BigDecimal buysumyear;

    /**
     * 本年累计付款金额
     */
    private BigDecimal paysumyear;

    /**
     * 最新采购日期
     */
    private String newbuydate;

    /**
     * 最新采购金额
     */
    private BigDecimal newbuysum;

    /**
     * 最新付款日期
     */
    private String newpaydate;

    /**
     * 最新付款金额
     */
    private BigDecimal newpaysum;

    /**
     * 实际占用金额
     */
    private BigDecimal realownsum;

    /**
     * 前30日采购金额
     */
    private BigDecimal buysummonth;

    /**
     * 禁用日期
     */
    private String disabledate;

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
     * 其他条件
     */
    private String otherconditions;

    /**
     * 建档人用户编号
     */
    private String adduserid;

    /**
     * 建档人姓名
     */
    private String addusername;

    /**
     * 建档人部门编号
     */
    private String adddeptid;

    /**
     * 建档部门名称
     */
    private String adddeptname;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改人姓名
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 启用人用户编号
     */
    private String openuserid;

    /**
     * 启用人姓名
     */
    private String openusername;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人用户编号
     */
    private String closeuserid;

    /**
     * 禁用人姓名
     */
    private String closeusername;

    /**
     * 禁用时间
     */
    private Date closetime;

    private BuySupplierDetailsort buySupplierDetailsort;
    /**
     * 联系人编号
     */
    private String contacterId;
    /**
     * 默认联系人
     */
    private String contacterDefaultSort;
    
    /**
     * 订单追加，1是，0否
     */
    private String orderappend;
    
    /**
     * 订单追加名称
     */
    private String orderappendname;

    /**
     * 所属部门
     */
    private String filiale;
    
    /**
     * 所属部门名称
     */
    private String filialename;

    /**
     * 财务联系人
     */
    private String financiallink;

    /**
     * 财务联系电话
     */
    private String financialmobile;

    /**
     * 财务联系人邮箱
     */
    private String financialemail;

    /**
     * 业务联系人
     */
    private String contact;
    
    /**
     * 业务联系人名称
     */
    private String contactname;
    
    /**
     * 业务联系电话
     */
    private String contactmobile;

    /**
     * 业务联系人邮箱
     */
    private String contactemail;

    /**
     * 区域主管
     */
    private String contactarea;

    /**
     * 区域主管联系电话
     */
    private String contactareamobile;

    /**
     * 区域主管邮箱
     */
    private String contactareaemail;

    /**
     * 大区/省区经理
     */
    private String region;

    /**
     * 大区/省区经理联系电话
     */
    private String regionmobile;

    /**
     * 大区/省区经理邮箱
     */
    private String regionemail;

    /**
     * 核销方式
     */
    private String canceltype;
    
    /**
     * 核销方式名称
     */
    private String canceltypename;

    /**
     * 年度目标
     */
    private BigDecimal annualobjectives;

    /**
     * 年度返利
     */
    private BigDecimal annualrebate;

    /**
     * 半年度返利
     */
    private BigDecimal semiannualrebate;

    /**
     * 季度返利
     */
    private BigDecimal quarterlyrebate;

    /**
     * 月度返利
     */
    private BigDecimal monthlyrebate;

    /**
     * 破损补贴
     */
    private BigDecimal breakagesubsidies;

    /**
     * 其他费用补贴
     */
    private BigDecimal othersubsidies;

    /**
     * 收回方式
     */
    private String recoverymode;
    
    /**
     * 收回方式名称
     */
    private String recoverymodename;

    /**
     * 供价折扣率
     */
    private BigDecimal pricediscount;

    /**
     * 促销员名额
     */
    private Integer promotersplaces;

    /**
     * 促销员工资
     */
    private BigDecimal promoterssalary;

    /**
     * 业务员名额
     */
    private Integer salesmanplaces;

    /**
     * 业务员工资
     */
    private BigDecimal salesmansalary;

    /**
     * @return 编码
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 名称(全称)
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称(全称)
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 状态4新增3暂存2保存1启用0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态4新增3暂存2保存1启用0禁用
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
     * @return 助记符
     */
    public String getSpell() {
        return spell;
    }

    /**
     * @param spell 
	 *            助记符
     */
    public void setSpell(String spell) {
        this.spell = spell == null ? null : spell.trim();
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
     * @return 法人代表身份证
     */
    public String getPersoncard() {
        return personcard;
    }

    /**
     * @param personcard 
	 *            法人代表身份证
     */
    public void setPersoncard(String personcard) {
        this.personcard = personcard == null ? null : personcard.trim();
    }

    /**
     * @return 企业性质
     */
    public String getNature() {
        return nature;
    }

    /**
     * @param nature 
	 *            企业性质
     */
    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    /**
     * @return 注册资金
     */
    public String getFund() {
        return fund;
    }

    /**
     * @param fund 
	 *            注册资金
     */
    public void setFund(String fund) {
        this.fund = fund == null ? null : fund.trim();
    }

    /**
     * @return 成立日期
     */
    public String getSetupdate() {
        return setupdate;
    }

    /**
     * @param setupdate 
	 *            成立日期
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
     * @return 年产值
     */
    public String getTurnoveryear() {
        return turnoveryear;
    }

    /**
     * @param turnoveryear 
	 *            年产值
     */
    public void setTurnoveryear(String turnoveryear) {
        this.turnoveryear = turnoveryear == null ? null : turnoveryear.trim();
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
     * @return 所属采购区域
     */
    public String getBuyarea() {
        return buyarea;
    }

    /**
     * @param buyarea 
	 *            所属采购区域
     */
    public void setBuyarea(String buyarea) {
        this.buyarea = buyarea == null ? null : buyarea.trim();
    }

    /**
     * @return 所属供应商分类
     */
    public String getSuppliersort() {
        return suppliersort;
    }

    /**
     * @param suppliersort 
	 *            所属供应商分类
     */
    public void setSuppliersort(String suppliersort) {
        this.suppliersort = suppliersort == null ? null : suppliersort.trim();
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
     * @return 信用日期
     */
    public String getCreditdate() {
        return creditdate;
    }

    /**
     * @param creditdate 
	 *            信用日期
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
     * @return 资金占用额度
     */
    public BigDecimal getOwnlimit() {
        return ownlimit;
    }

    /**
     * @param ownlimit 
	 *            资金占用额度
     */
    public void setOwnlimit(BigDecimal ownlimit) {
        this.ownlimit = ownlimit;
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
     * @return 默认采购部门(来自部门档案)
     */
    public String getBuydeptid() {
        return buydeptid;
    }

    /**
     * @param buydeptid 
	 *            默认采购部门(来自部门档案)
     */
    public void setBuydeptid(String buydeptid) {
        this.buydeptid = buydeptid == null ? null : buydeptid.trim();
    }

    /**
     * @return 默认采购员（来自人员档案）
     */
    public String getBuyuserid() {
        return buyuserid;
    }

    /**
     * @param buyuserid 
	 *            默认采购员（来自人员档案）
     */
    public void setBuyuserid(String buyuserid) {
        this.buyuserid = buyuserid == null ? null : buyuserid.trim();
    }

    /**
     * @return 采购员联系电话
     */
    public String getBuyusermobile() {
        return buyusermobile;
    }

    /**
     * @param buyusermobile 
	 *            采购员联系电话
     */
    public void setBuyusermobile(String buyusermobile) {
        this.buyusermobile = buyusermobile == null ? null : buyusermobile.trim();
    }

    /**
     * @return 默认仓库(来自仓库档案)
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            默认仓库(来自仓库档案)
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 累计采购金额
     */
    public BigDecimal getBuysum() {
        return buysum;
    }

    /**
     * @param buysum 
	 *            累计采购金额
     */
    public void setBuysum(BigDecimal buysum) {
        this.buysum = buysum;
    }

    /**
     * @return 累计付款金额
     */
    public BigDecimal getPaysum() {
        return paysum;
    }

    /**
     * @param paysum 
	 *            累计付款金额
     */
    public void setPaysum(BigDecimal paysum) {
        this.paysum = paysum;
    }

    /**
     * @return 应付金额
     */
    public BigDecimal getPayable() {
        return payable;
    }

    /**
     * @param payable 
	 *            应付金额
     */
    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    /**
     * @return 其他应收金额
     */
    public BigDecimal getOtherduefromsum() {
        return otherduefromsum;
    }

    /**
     * @param otherduefromsum 
	 *            其他应收金额
     */
    public void setOtherduefromsum(BigDecimal otherduefromsum) {
        this.otherduefromsum = otherduefromsum;
    }

    /**
     * @return 本年累计采购金额
     */
    public BigDecimal getBuysumyear() {
        return buysumyear;
    }

    /**
     * @param buysumyear 
	 *            本年累计采购金额
     */
    public void setBuysumyear(BigDecimal buysumyear) {
        this.buysumyear = buysumyear;
    }

    /**
     * @return 本年累计付款金额
     */
    public BigDecimal getPaysumyear() {
        return paysumyear;
    }

    /**
     * @param paysumyear 
	 *            本年累计付款金额
     */
    public void setPaysumyear(BigDecimal paysumyear) {
        this.paysumyear = paysumyear;
    }

    /**
     * @return 最新采购日期
     */
    public String getNewbuydate() {
        return newbuydate;
    }

    /**
     * @param newbuydate 
	 *            最新采购日期
     */
    public void setNewbuydate(String newbuydate) {
        this.newbuydate = newbuydate == null ? null : newbuydate.trim();
    }

    /**
     * @return 最新采购金额
     */
    public BigDecimal getNewbuysum() {
        return newbuysum;
    }

    /**
     * @param newbuysum 
	 *            最新采购金额
     */
    public void setNewbuysum(BigDecimal newbuysum) {
        this.newbuysum = newbuysum;
    }

    /**
     * @return 最新付款日期
     */
    public String getNewpaydate() {
        return newpaydate;
    }

    /**
     * @param newpaydate 
	 *            最新付款日期
     */
    public void setNewpaydate(String newpaydate) {
        this.newpaydate = newpaydate == null ? null : newpaydate.trim();
    }

    /**
     * @return 最新付款金额
     */
    public BigDecimal getNewpaysum() {
        return newpaysum;
    }

    /**
     * @param newpaysum 
	 *            最新付款金额
     */
    public void setNewpaysum(BigDecimal newpaysum) {
        this.newpaysum = newpaysum;
    }

    /**
     * @return 实际占用金额
     */
    public BigDecimal getRealownsum() {
        return realownsum;
    }

    /**
     * @param realownsum 
	 *            实际占用金额
     */
    public void setRealownsum(BigDecimal realownsum) {
        this.realownsum = realownsum;
    }

    /**
     * @return 前30日采购金额
     */
    public BigDecimal getBuysummonth() {
        return buysummonth;
    }

    /**
     * @param buysummonth 
	 *            前30日采购金额
     */
    public void setBuysummonth(BigDecimal buysummonth) {
        this.buysummonth = buysummonth;
    }

    /**
     * @return 禁用日期
     */
    public String getDisabledate() {
        return disabledate;
    }

    /**
     * @param disabledate 
	 *            禁用日期
     */
    public void setDisabledate(String disabledate) {
        this.disabledate = disabledate == null ? null : disabledate.trim();
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
     * @return 建档人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            建档人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 建档人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 建档部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            建档部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 建档时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            建档时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
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

    /**
     * @return 修改人姓名
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改人姓名
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
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
     * @return 启用人用户编号
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人用户编号
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
    }

    /**
     * @return 启用人姓名
     */
    public String getOpenusername() {
        return openusername;
    }

    /**
     * @param openusername 
	 *            启用人姓名
     */
    public void setOpenusername(String openusername) {
        this.openusername = openusername == null ? null : openusername.trim();
    }

    /**
     * @return 启用时间
     */
    public Date getOpentime() {
        return opentime;
    }

    /**
     * @param opentime 
	 *            启用时间
     */
    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    /**
     * @return 禁用人用户编号
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人用户编号
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    /**
     * @return 禁用人姓名
     */
    public String getCloseusername() {
        return closeusername;
    }

    /**
     * @param closeusername 
	 *            禁用人姓名
     */
    public void setCloseusername(String closeusername) {
        this.closeusername = closeusername == null ? null : closeusername.trim();
    }

    /**
     * @return 禁用时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            禁用时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 订单追加，1是，0否
     */
    public String getOrderappend() {
        return orderappend;
    }

    /**
     * @param orderappend 
	 *            订单追加，1是，0否
     */
    public void setOrderappend(String orderappend) {
        this.orderappend = orderappend == null ? null : orderappend.trim();
    }

    /**
     * @return 所属分公司
     */
    public String getFiliale() {
        return filiale;
    }

    /**
     * @param filiale 
	 *            所属分公司
     */
    public void setFiliale(String filiale) {
        this.filiale = filiale == null ? null : filiale.trim();
    }

    /**
     * @return 财务联系人
     */
    public String getFinanciallink() {
        return financiallink;
    }

    /**
     * @param financiallink 
	 *            财务联系人
     */
    public void setFinanciallink(String financiallink) {
        this.financiallink = financiallink == null ? null : financiallink.trim();
    }

    /**
     * @return 财务联系电话
     */
    public String getFinancialmobile() {
        return financialmobile;
    }

    /**
     * @param financialmobile 
	 *            财务联系电话
     */
    public void setFinancialmobile(String financialmobile) {
        this.financialmobile = financialmobile == null ? null : financialmobile.trim();
    }

    /**
     * @return 财务联系人邮箱
     */
    public String getFinancialemail() {
        return financialemail;
    }

    /**
     * @param financialemail 
	 *            财务联系人邮箱
     */
    public void setFinancialemail(String financialemail) {
        this.financialemail = financialemail == null ? null : financialemail.trim();
    }

    /**
     * @return 区域主管
     */
    public String getContactarea() {
        return contactarea;
    }

    /**
     * @param contactarea 
	 *            区域主管
     */
    public void setContactarea(String contactarea) {
        this.contactarea = contactarea == null ? null : contactarea.trim();
    }

    /**
     * @return 区域主管联系电话
     */
    public String getContactareamobile() {
        return contactareamobile;
    }

    /**
     * @param contactareamobile 
	 *            区域主管联系电话
     */
    public void setContactareamobile(String contactareamobile) {
        this.contactareamobile = contactareamobile == null ? null : contactareamobile.trim();
    }

    /**
     * @return 区域主管邮箱
     */
    public String getContactareaemail() {
        return contactareaemail;
    }

    /**
     * @param contactareaemail 
	 *            区域主管邮箱
     */
    public void setContactareaemail(String contactareaemail) {
        this.contactareaemail = contactareaemail == null ? null : contactareaemail.trim();
    }

    /**
     * @return 大区/省区经理
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region 
	 *            大区/省区经理
     */
    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    /**
     * @return 大区/省区经理联系电话
     */
    public String getRegionmobile() {
        return regionmobile;
    }

    /**
     * @param regionmobile 
	 *            大区/省区经理联系电话
     */
    public void setRegionmobile(String regionmobile) {
        this.regionmobile = regionmobile == null ? null : regionmobile.trim();
    }

    /**
     * @return 大区/省区经理邮箱
     */
    public String getRegionemail() {
        return regionemail;
    }

    /**
     * @param regionemail 
	 *            大区/省区经理邮箱
     */
    public void setRegionemail(String regionemail) {
        this.regionemail = regionemail == null ? null : regionemail.trim();
    }

    /**
     * @return 核销方式
     */
    public String getCanceltype() {
        return canceltype;
    }

    /**
     * @param canceltype 
	 *            核销方式
     */
    public void setCanceltype(String canceltype) {
        this.canceltype = canceltype == null ? null : canceltype.trim();
    }

    /**
     * @return 年度目标
     */
    public BigDecimal getAnnualobjectives() {
        return annualobjectives;
    }

    /**
     * @param annualobjectives 
	 *            年度目标
     */
    public void setAnnualobjectives(BigDecimal annualobjectives) {
        this.annualobjectives = annualobjectives;
    }

    /**
     * @return 年度返利
     */
    public BigDecimal getAnnualrebate() {
        return annualrebate;
    }

    /**
     * @param annualrebate 
	 *            年度返利
     */
    public void setAnnualrebate(BigDecimal annualrebate) {
        this.annualrebate = annualrebate;
    }

    /**
     * @return 半年度返利
     */
    public BigDecimal getSemiannualrebate() {
        return semiannualrebate;
    }

    /**
     * @param semiannualrebate 
	 *            半年度返利
     */
    public void setSemiannualrebate(BigDecimal semiannualrebate) {
        this.semiannualrebate = semiannualrebate;
    }

    /**
     * @return 季度返利
     */
    public BigDecimal getQuarterlyrebate() {
        return quarterlyrebate;
    }

    /**
     * @param quarterlyrebate 
	 *            季度返利
     */
    public void setQuarterlyrebate(BigDecimal quarterlyrebate) {
        this.quarterlyrebate = quarterlyrebate;
    }

    /**
     * @return 月度返利
     */
    public BigDecimal getMonthlyrebate() {
        return monthlyrebate;
    }

    /**
     * @param monthlyrebate 
	 *            月度返利
     */
    public void setMonthlyrebate(BigDecimal monthlyrebate) {
        this.monthlyrebate = monthlyrebate;
    }

    /**
     * @return 破损补贴
     */
    public BigDecimal getBreakagesubsidies() {
        return breakagesubsidies;
    }

    /**
     * @param breakagesubsidies 
	 *            破损补贴
     */
    public void setBreakagesubsidies(BigDecimal breakagesubsidies) {
        this.breakagesubsidies = breakagesubsidies;
    }

    /**
     * @return 其他费用补贴
     */
    public BigDecimal getOthersubsidies() {
        return othersubsidies;
    }

    /**
     * @param othersubsidies 
	 *            其他费用补贴
     */
    public void setOthersubsidies(BigDecimal othersubsidies) {
        this.othersubsidies = othersubsidies;
    }

    /**
     * @return 收回方式
     */
    public String getRecoverymode() {
        return recoverymode;
    }

    /**
     * @param recoverymode 
	 *            收回方式
     */
    public void setRecoverymode(String recoverymode) {
        this.recoverymode = recoverymode == null ? null : recoverymode.trim();
    }

    /**
     * @return 供价折扣率
     */
    public BigDecimal getPricediscount() {
        return pricediscount;
    }

    /**
     * @param pricediscount 
	 *            供价折扣率
     */
    public void setPricediscount(BigDecimal pricediscount) {
        this.pricediscount = pricediscount;
    }

    /**
     * @return 促销员名额
     */
    public Integer getPromotersplaces() {
        return promotersplaces;
    }

    /**
     * @param promotersplaces 
	 *            促销员名额
     */
    public void setPromotersplaces(Integer promotersplaces) {
        this.promotersplaces = promotersplaces;
    }

    /**
     * @return 促销员工资
     */
    public BigDecimal getPromoterssalary() {
        return promoterssalary;
    }

    /**
     * @param promoterssalary 
	 *            促销员工资
     */
    public void setPromoterssalary(BigDecimal promoterssalary) {
        this.promoterssalary = promoterssalary;
    }

    /**
     * @return 业务员名额
     */
    public Integer getSalesmanplaces() {
        return salesmanplaces;
    }

    /**
     * @param salesmanplaces 
	 *            业务员名额
     */
    public void setSalesmanplaces(Integer salesmanplaces) {
        this.salesmanplaces = salesmanplaces;
    }

    /**
     * @return 业务员工资
     */
    public BigDecimal getSalesmansalary() {
        return salesmansalary;
    }

    /**
     * @param salesmansalary 
	 *            业务员工资
     */
    public void setSalesmansalary(BigDecimal salesmansalary) {
        this.salesmansalary = salesmansalary;
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
     * @return 其他条件
     */
    public String getOtherconditions() {
        return otherconditions;
    }

    /**
     * @param otherconditions 
	 *            其他条件
     */
    public void setOtherconditions(String otherconditions) {
        this.otherconditions = otherconditions == null ? null : otherconditions.trim();
    }

	public String getOldid() {
		return oldid;
	}

	public void setOldid(String oldid) {
		this.oldid = oldid;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getSuppliersortname() {
		return suppliersortname;
	}

	public void setSuppliersortname(String suppliersortname) {
		this.suppliersortname = suppliersortname;
	}

	public String getSettletypename() {
		return settletypename;
	}

	public void setSettletypename(String settletypename) {
		this.settletypename = settletypename;
	}

	public String getBuydeptname() {
		return buydeptname;
	}

	public void setBuydeptname(String buydeptname) {
		this.buydeptname = buydeptname;
	}

	public String getBuyusername() {
		return buyusername;
	}

	public void setBuyusername(String buyusername) {
		this.buyusername = buyusername;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public BuySupplierDetailsort getBuySupplierDetailsort() {
		return buySupplierDetailsort;
	}

	public void setBuySupplierDetailsort(BuySupplierDetailsort buySupplierDetailsort) {
		this.buySupplierDetailsort = buySupplierDetailsort;
	}

	public String getContacterId() {
		return contacterId;
	}

	public void setContacterId(String contacterId) {
		this.contacterId = contacterId;
	}

	public String getContacterDefaultSort() {
		return contacterDefaultSort;
	}

	public void setContacterDefaultSort(String contacterDefaultSort) {
		this.contacterDefaultSort = contacterDefaultSort;
	}

	public String getCanceltypename() {
		return canceltypename;
	}

	public void setCanceltypename(String canceltypename) {
		this.canceltypename = canceltypename;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactemail() {
		return contactemail;
	}

	public void setContactemail(String contactemail) {
		this.contactemail = contactemail;
	}

	public String getContactmobile() {
		return contactmobile;
	}

	public void setContactmobile(String contactmobile) {
		this.contactmobile = contactmobile;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getFilialename() {
		return filialename;
	}

	public void setFilialename(String filialename) {
		this.filialename = filialename;
	}

	public String getOrderappendname() {
		return orderappendname;
	}

	public void setOrderappendname(String orderappendname) {
		this.orderappendname = orderappendname;
	}

	public String getRecoverymodename() {
		return recoverymodename;
	}

	public void setRecoverymodename(String recoverymodename) {
		this.recoverymodename = recoverymodename;
	}

	public String getBuyareaname() {
		return buyareaname;
	}

	public void setBuyareaname(String buyareaname) {
		this.buyareaname = buyareaname;
	}
}

