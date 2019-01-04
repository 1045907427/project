package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hd.agent.common.util.PageData;

/**
 * 客户档案
 * @author zhengziyong
 *
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号（上级编号+本级编号）
     */
    private String id;
    
    private String oldid;
    
    /**
     * 要删除的合同商品编码数组字符串
     */
    private String delPriceList;

    /**
     * 名称
     */
    private String name;
    
    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 本级编号
     */
    private String thisid;

    /**
     * 上级客户
     */
    private String pid;
    
    /**
     * 上级客户名称
     */
    private String pname;
    
    /**
     * 店号
     */
    private String shopno;

    /**
     * 状态
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
     * 建档人
     */
    private String adduserid;

    /**
     * 建档人
     */
    private String addusername;

    /**
     * 建档部门
     */
    private String adddeptid;

    /**
     * 建档部门
     */
    private String adddeptname;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 最后修改人
     */
    private String modifyuserid;

    /**
     * 最后修改人
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 启用人
     */
    private String openuserid;

    /**
     * 启用人
     */
    private String openusername;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人
     */
    private String closeuserid;

    /**
     * 禁用人
     */
    private String closeusername;

    /**
     * 禁用时间
     */
    private Date closetime;

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
     * ABC等级名称
     */
    private String abclevelname;

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
     * 法人
     */
    private String person;

    /**
     * 公司属性
     */
    private String nature;
    
    /**
     * 公司属性名称
     */
    private String naturename;

    /**
     * 注册资金
     */
    private BigDecimal fund;

    /**
     * 成立时间
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
     * 原来的所属区域
     */
    private String oldsalesarea;
    /**
     * 所属区域名称
     */
    private String salesareaname;
    /**
     * 所属分类
     */
    private String customersort;
    
    /**
     * 原来所属分类
     */
    private String oldcustomersort;
    /**
     * 所属分类名称
     */
    private String customersortname;
    /**
     * 对方联系人
     */
    private String contact;
    /**
     * 对方联系人名称
     */
    private String contactname;
    
    /**
     * 联系人号码
     */
    private String contactmobile;
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 促销分类
     */
    private String promotionsort;
    
    /**
     * 促销分类名称
     */
    private String promotionsortname;

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
     * 银行账号
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
     * 是否结算
     */
    private String settlement;

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
     * 支付方式
     */
    private String paytype;
    
    /**
     * 支付方式名称
     */
    private String paytypename;

    /**
     * 是否开票
     */
    private String billing;

    /**
     * 应收依据
     */
    private String billtype;

    /**
     * 超账期控制
     */
    private String overcontrol;
    
    /**
     * 超账期控制名称
     */
    private String overcontrolname;

    /**
     * 超账期宽限天数
     */
    private Integer overgracedate;
    
    /**
     * 核销方式
     */
    private String canceltype;
    
    /**
     * 核销方式名称
     */
    private String canceltypename;

    /**
     * 默认销售部门
     */
    private String salesdeptid;
    
    /**
     * 原来的默认销售部门
     */
    private String oldsalesdeptid;

    /**
     * 默认销售部门名称
     */
    private String salesdeptname;

    /**
     * 默认业务员
     */
    private String salesuserid;
    
    /**
     * 原来的默认业务员
     */
    private String oldsalesuserid;

    /**
     * 默认业务员名称
     */
    private String salesusername;

    /**
     * 默认价格套
     */
    private String pricesort;
    
    /**
     * 默认价格套名称
     */
    private String pricesortname;

    /**
     * 默认理货员
     */
    private String tallyuserid;

    /**
     * 默认理货员名称
     */
    private String tallyusername;
    
    /**
     * 默认内勤
     */
    private String indoorstaff;
    
    /**
     * 默认内勤名称
     */
    private String indoorstaffname;
    
    /**
     * 收款人
     */
    private String payeeid;
    
    /**
     * 收款人名称
     */
    private String payeename;
    
    /**
     * 原来的默认内勤
     */
    private String oldindoorstaff;

    /**
     * 对应供应商
     */
    private String supplier;

    /**
     * 计划毛利率
     */
    private BigDecimal margin;

    /**
     * 累计销售金额
     */
    private BigDecimal allsalessum;

    /**
     * 累计收款金额
     */
    private BigDecimal allcollectionsum;

    /**
     * 应收金额
     */
    private BigDecimal duefromsum;

    /**
     * 其他应付金额
     */
    private BigDecimal otherpayablesum;

    /**
     * 本年累计销售金额
     */
    private BigDecimal allsalessumyear;

    /**
     * 本年累计收款金额
     */
    private BigDecimal allcollectionsumyear;

    /**
     * 最新销售日期
     */
    private String newsalesdate;

    /**
     * 最新销售金额
     */
    private BigDecimal newsalessum;

    /**
     * 最新收款日期
     */
    private String newcollectdate;

    /**
     * 最新收款金额
     */
    private BigDecimal newcollectsum;

    /**
     * 前30日销售金额
     */
    private BigDecimal salessummonth;

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
     * 是否末级
     */
    private String islast;
    
    /**
     * 是否末级名称
     */
    private String islastname;
    
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
     * 是否连锁名称
     */
    private String ischainname;
    
    /**
     * 县级市
     */
    private String countylevel;
    
    /**
     * 乡镇
     */
    private String villagetown;
    
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
     * 票种名称
     */
    private String tickettypename;
    
    /**
     * 是否现款
     */
    private String iscash;
    
    /**
     * 是否现款名称
     */
    private String iscashname;
    
    /**
     * 是否账期
     */
    private String islongterm;
    
    /**
     * 是否账期名称
     */
    private String islongtermname;
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
     * 法人代表电话
     */
    private String personmobile;
    
    /**
     * 法人身份证号码
     */
    private String personcard;
    
    /**
     * 信用等级
     */
    private String creditrating;
    
    /**
     * 信用等级名称
     */
    private String creditratingname;
    
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
    
    private String[] contacterId;
    private String[] contacterDefaultSort;
    
    /**
     * 客户档案和客户关系对应关系实体
     */
    public CustomerAndSort customerAndSort;
    
    /**
     * 客户关系列表
     */
    public List<CustomerSort> sortList;
    
    /**
     * 合同价
     */
    public List<CustomerPrice> priceList;
    
    /**
     * 合同价分页
     */
    public PageData pricePageData;
    /**
     * 客户余额
     */
    private BigDecimal amount;
    
    /**
     * 排序
     */
    private Integer seq;
    
    /**
     * 是否最小单位发货
     */
    private String issendminimum;
    
    /**
     * @return 编号（上级编号+本级编号）
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号（上级编号+本级编号）
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 本级编号
     */
    public String getThisid() {
        return thisid;
    }

    /**
     * @param thisid 
	 *            本级编号
     */
    public void setThisid(String thisid) {
        this.thisid = thisid;
    }

    /**
     * @return 上级客户
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            上级客户
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
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
        this.state = state;
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
        this.remark = remark;
    }

    /**
     * @return 建档人
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    /**
     * @return 建档人
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            建档人
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername;
    }

    /**
     * @return 建档部门
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档部门
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid;
    }

    /**
     * @return 建档部门
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            建档部门
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname;
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
     * @return 最后修改人
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
    }

    /**
     * @return 最后修改人
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername;
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
     * @return 启用人
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid;
    }

    /**
     * @return 启用人
     */
    public String getOpenusername() {
        return openusername;
    }

    /**
     * @param openusername 
	 *            启用人
     */
    public void setOpenusername(String openusername) {
        this.openusername = openusername;
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
     * @return 禁用人
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid;
    }

    /**
     * @return 禁用人
     */
    public String getCloseusername() {
        return closeusername;
    }

    /**
     * @param closeusername 
	 *            禁用人
     */
    public void setCloseusername(String closeusername) {
        this.closeusername = closeusername;
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
        this.shortname = shortname;
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
        this.shortcode = shortcode;
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
        this.abclevel = abclevel;
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
        this.telphone = telphone;
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
        this.faxno = faxno;
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
        this.email = email;
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
        this.mobile = mobile;
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
        this.website = website;
    }

    /**
     * @return 法人
     */
    public String getPerson() {
        return person;
    }

    /**
     * @param person 
	 *            法人
     */
    public void setPerson(String person) {
        this.person = person;
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
        this.nature = nature;
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
     * @return 成立时间
     */
    public String getSetupdate() {
        return setupdate;
    }

    /**
     * @param setupdate 
	 *            成立时间
     */
    public void setSetupdate(String setupdate) {
        this.setupdate = setupdate;
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
        this.staffnum = staffnum;
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
        this.firstbusinessdate = firstbusinessdate;
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
        this.salesarea = salesarea;
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
        this.customersort = customersort;
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
        this.contact = contact;
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
        this.address = address;
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
        this.zip = zip;
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
        this.taxno = taxno;
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
        this.bank = bank;
    }

    /**
     * @return 银行账号
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno 
	 *            银行账号
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
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
        this.creditdate = creditdate;
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
        this.settlement = settlement;
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
        this.settletype = settletype;
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
        this.settleday = settleday;
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
        this.paytype = paytype;
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
        this.billing = billing;
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
        this.billtype = billtype;
    }

    /**
     * @return 超账期控制
     */
    public String getOvercontrol() {
        return overcontrol;
    }

    /**
     * @param overcontrol 
	 *            超账期控制
     */
    public void setOvercontrol(String overcontrol) {
        this.overcontrol = overcontrol;
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
        this.canceltype = canceltype;
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
        this.salesdeptid = salesdeptid;
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
        this.salesdeptname = salesdeptname;
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
        this.salesuserid = salesuserid;
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
        this.salesusername = salesusername;
    }

    /**
     * @return 默认价格级别
     */
    public String getPricesort() {
        return pricesort;
    }

    /**
     * @param pricesort 
	 *            默认价格级别
     */
    public void setPricesort(String pricesort) {
        this.pricesort = pricesort;
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
        this.tallyuserid = tallyuserid;
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
        this.tallyusername = tallyusername;
    }

    /**
     * @return 对应供应商
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * @param supplier 
	 *            对应供应商
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * @return 累计销售金额
     */
    public BigDecimal getAllsalessum() {
        return allsalessum;
    }

    /**
     * @param allsalessum 
	 *            累计销售金额
     */
    public void setAllsalessum(BigDecimal allsalessum) {
        this.allsalessum = allsalessum;
    }

    /**
     * @return 累计收款金额
     */
    public BigDecimal getAllcollectionsum() {
        return allcollectionsum;
    }

    /**
     * @param allcollectionsum 
	 *            累计收款金额
     */
    public void setAllcollectionsum(BigDecimal allcollectionsum) {
        this.allcollectionsum = allcollectionsum;
    }

    /**
     * @return 应收金额
     */
    public BigDecimal getDuefromsum() {
        return duefromsum;
    }

    /**
     * @param duefromsum 
	 *            应收金额
     */
    public void setDuefromsum(BigDecimal duefromsum) {
        this.duefromsum = duefromsum;
    }

    /**
     * @return 其他应付金额
     */
    public BigDecimal getOtherpayablesum() {
        return otherpayablesum;
    }

    /**
     * @param otherpayablesum 
	 *            其他应付金额
     */
    public void setOtherpayablesum(BigDecimal otherpayablesum) {
        this.otherpayablesum = otherpayablesum;
    }

    /**
     * @return 本年累计销售金额
     */
    public BigDecimal getAllsalessumyear() {
        return allsalessumyear;
    }

    /**
     * @param allsalessumyear 
	 *            本年累计销售金额
     */
    public void setAllsalessumyear(BigDecimal allsalessumyear) {
        this.allsalessumyear = allsalessumyear;
    }

    /**
     * @return 本年累计收款金额
     */
    public BigDecimal getAllcollectionsumyear() {
        return allcollectionsumyear;
    }

    /**
     * @param allcollectionsumyear 
	 *            本年累计收款金额
     */
    public void setAllcollectionsumyear(BigDecimal allcollectionsumyear) {
        this.allcollectionsumyear = allcollectionsumyear;
    }

    /**
     * @return 最新销售日期
     */
    public String getNewsalesdate() {
        return newsalesdate;
    }

    /**
     * @param newsalesdate 
	 *            最新销售日期
     */
    public void setNewsalesdate(String newsalesdate) {
        this.newsalesdate = newsalesdate;
    }

    /**
     * @return 最新销售金额
     */
    public BigDecimal getNewsalessum() {
        return newsalessum;
    }

    /**
     * @param newsalessum 
	 *            最新销售金额
     */
    public void setNewsalessum(BigDecimal newsalessum) {
        this.newsalessum = newsalessum;
    }

    /**
     * @return 最新收款日期
     */
    public String getNewcollectdate() {
        return newcollectdate;
    }

    /**
     * @param newcollectdate 
	 *            最新收款日期
     */
    public void setNewcollectdate(String newcollectdate) {
        this.newcollectdate = newcollectdate;
    }

    /**
     * @return 最新收款金额
     */
    public BigDecimal getNewcollectsum() {
        return newcollectsum;
    }

    /**
     * @param newcollectsum 
	 *            最新收款金额
     */
    public void setNewcollectsum(BigDecimal newcollectsum) {
        this.newcollectsum = newcollectsum;
    }

    /**
     * @return 前30日销售金额
     */
    public BigDecimal getSalessummonth() {
        return salessummonth;
    }

    /**
     * @param salessummonth 
	 *            前30日销售金额
     */
    public void setSalessummonth(BigDecimal salessummonth) {
        this.salessummonth = salessummonth;
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
        this.field01 = field01;
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
        this.field02 = field02;
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
        this.field03 = field03;
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
        this.field04 = field04;
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
        this.field05 = field05;
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
        this.field06 = field06;
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
        this.field07 = field07;
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
        this.field08 = field08;
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
        this.field09 = field09;
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
        this.field10 = field10;
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
        this.field11 = field11;
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
        this.field12 = field12;
    }

	public String getOldid() {
		return oldid;
	}

	public void setOldid(String oldid) {
		this.oldid = oldid;
	}

	public String[] getContacterId() {
		return contacterId;
	}

	public void setContacterId(String[] contacterId) {
		this.contacterId = contacterId;
	}

	public String[] getContacterDefaultSort() {
		return contacterDefaultSort;
	}

	public void setContacterDefaultSort(String[] contacterDefaultSort) {
		this.contacterDefaultSort = contacterDefaultSort;
	}

	public CustomerAndSort getCustomerAndSort() {
		return customerAndSort;
	}

	public void setCustomerAndSort(CustomerAndSort customerAndSort) {
		this.customerAndSort = customerAndSort;
	}

	public List<CustomerSort> getSortList() {
		return sortList;
	}

	public void setSortList(List<CustomerSort> sortList) {
		this.sortList = sortList;
	}

	public List<CustomerPrice> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<CustomerPrice> priceList) {
		this.priceList = priceList;
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}

	public String getCustomersortname() {
		return customersortname;
	}

	public void setCustomersortname(String customersortname) {
		this.customersortname = customersortname;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public PageData getPricePageData() {
		return pricePageData;
	}

	public void setPricePageData(PageData pricePageData) {
		this.pricePageData = pricePageData;
	}

	public String getIndoorstaff() {
		return indoorstaff;
	}

	public void setIndoorstaff(String indoorstaff) {
		this.indoorstaff = indoorstaff;
	}

	public String getDelPriceList() {
		return delPriceList;
	}

	public void setDelPriceList(String delPriceList) {
		this.delPriceList = delPriceList;
	}

	public BigDecimal getMargin() {
		return margin;
	}

	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}

	public String getContactmobile() {
		return contactmobile;
	}

	public void setContactmobile(String contactmobile) {
		this.contactmobile = contactmobile;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getIslast() {
		return islast;
	}

	public void setIslast(String islast) {
		this.islast = islast;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPromotionsort() {
		return promotionsort;
	}

	public void setPromotionsort(String promotionsort) {
		this.promotionsort = promotionsort;
	}

	public String getCaraccount() {
		return caraccount;
	}

	public void setCaraccount(String caraccount) {
		this.caraccount = caraccount;
	}

	public String getIschain() {
		return ischain;
	}

	public void setIschain(String ischain) {
		this.ischain = ischain;
	}

	public String getCountylevel() {
		return countylevel;
	}

	public void setCountylevel(String countylevel) {
		this.countylevel = countylevel;
	}

	public String getVillagetown() {
		return villagetown;
	}

	public void setVillagetown(String villagetown) {
		this.villagetown = villagetown;
	}

	public String getReconciliationdate() {
		return reconciliationdate;
	}

	public void setReconciliationdate(String reconciliationdate) {
		this.reconciliationdate = reconciliationdate;
	}

	public String getBillingdate() {
		return billingdate;
	}

	public void setBillingdate(String billingdate) {
		this.billingdate = billingdate;
	}

	public String getArrivalamountdate() {
		return arrivalamountdate;
	}

	public void setArrivalamountdate(String arrivalamountdate) {
		this.arrivalamountdate = arrivalamountdate;
	}

	public String getTickettype() {
		return tickettype;
	}

	public void setTickettype(String tickettype) {
		this.tickettype = tickettype;
	}

	public String getIscash() {
		return iscash;
	}

	public void setIscash(String iscash) {
		this.iscash = iscash;
	}

	public String getIslongterm() {
		return islongterm;
	}

	public void setIslongterm(String islongterm) {
		this.islongterm = islongterm;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getCheckmobile() {
		return checkmobile;
	}

	public void setCheckmobile(String checkmobile) {
		this.checkmobile = checkmobile;
	}

	public String getCheckemail() {
		return checkemail;
	}

	public void setCheckemail(String checkemail) {
		this.checkemail = checkemail;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayermobile() {
		return payermobile;
	}

	public void setPayermobile(String payermobile) {
		this.payermobile = payermobile;
	}

	public String getPayeremail() {
		return payeremail;
	}

	public void setPayeremail(String payeremail) {
		this.payeremail = payeremail;
	}

	public String getShopmanager() {
		return shopmanager;
	}

	public void setShopmanager(String shopmanager) {
		this.shopmanager = shopmanager;
	}

	public String getShopmanagermobile() {
		return shopmanagermobile;
	}

	public void setShopmanagermobile(String shopmanagermobile) {
		this.shopmanagermobile = shopmanagermobile;
	}

	public String getGsreceipter() {
		return gsreceipter;
	}

	public void setGsreceipter(String gsreceipter) {
		this.gsreceipter = gsreceipter;
	}

	public String getGsreceiptermobile() {
		return gsreceiptermobile;
	}

	public void setGsreceiptermobile(String gsreceiptermobile) {
		this.gsreceiptermobile = gsreceiptermobile;
	}

	public String getSettletypename() {
		return settletypename;
	}

	public void setSettletypename(String settletypename) {
		this.settletypename = settletypename;
	}

	public String getPricesortname() {
		return pricesortname;
	}

	public void setPricesortname(String pricesortname) {
		this.pricesortname = pricesortname;
	}

	public String getPromotionsortname() {
		return promotionsortname;
	}

	public void setPromotionsortname(String promotionsortname) {
		this.promotionsortname = promotionsortname;
	}

	public String getPersonmobile() {
		return personmobile;
	}

	public void setPersonmobile(String personmobile) {
		this.personmobile = personmobile;
	}

	public String getPersoncard() {
		return personcard;
	}

	public void setPersoncard(String personcard) {
		this.personcard = personcard;
	}

	public String getCreditrating() {
		return creditrating;
	}

	public void setCreditrating(String creditrating) {
		this.creditrating = creditrating;
	}

	public BigDecimal getTargetsales() {
		return targetsales;
	}

	public void setTargetsales(BigDecimal targetsales) {
		this.targetsales = targetsales;
	}

	public BigDecimal getYearback() {
		return yearback;
	}

	public void setYearback(BigDecimal yearback) {
		this.yearback = yearback;
	}

	public BigDecimal getMonthback() {
		return monthback;
	}

	public void setMonthback(BigDecimal monthback) {
		this.monthback = monthback;
	}

	public BigDecimal getDispatchingamount() {
		return dispatchingamount;
	}

	public void setDispatchingamount(BigDecimal dispatchingamount) {
		this.dispatchingamount = dispatchingamount;
	}

	public BigDecimal getSixone() {
		return sixone;
	}

	public void setSixone(BigDecimal sixone) {
		this.sixone = sixone;
	}

	public BigDecimal getStorearea() {
		return storearea;
	}

	public void setStorearea(BigDecimal storearea) {
		this.storearea = storearea;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getShopno() {
		return shopno;
	}

	public void setShopno(String shopno) {
		this.shopno = shopno;
	}
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOldsalesarea() {
		return oldsalesarea;
	}

	public void setOldsalesarea(String oldsalesarea) {
		this.oldsalesarea = oldsalesarea;
	}

	public String getOldcustomersort() {
		return oldcustomersort;
	}

	public void setOldcustomersort(String oldcustomersort) {
		this.oldcustomersort = oldcustomersort;
	}

	public String getOldsalesdeptid() {
		return oldsalesdeptid;
	}

	public void setOldsalesdeptid(String oldsalesdeptid) {
		this.oldsalesdeptid = oldsalesdeptid;
	}

	public String getOldsalesuserid() {
		return oldsalesuserid;
	}

	public void setOldsalesuserid(String oldsalesuserid) {
		this.oldsalesuserid = oldsalesuserid;
	}

	public String getOldindoorstaff() {
		return oldindoorstaff;
	}

	public void setOldindoorstaff(String oldindoorstaff) {
		this.oldindoorstaff = oldindoorstaff;
	}

	public String getPayeeid() {
		return payeeid;
	}

	public void setPayeeid(String payeeid) {
		this.payeeid = payeeid;
	}

	public String getPayeename() {
		return payeename;
	}

	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getIssendminimum() {
		return issendminimum;
	}

	public void setIssendminimum(String issendminimum) {
		this.issendminimum = issendminimum;
	}

	public String getAbclevelname() {
		return abclevelname;
	}

	public void setAbclevelname(String abclevelname) {
		this.abclevelname = abclevelname;
	}

	public String getNaturename() {
		return naturename;
	}

	public void setNaturename(String naturename) {
		this.naturename = naturename;
	}

	public String getPaytypename() {
		return paytypename;
	}

	public void setPaytypename(String paytypename) {
		this.paytypename = paytypename;
	}

	public String getOvercontrolname() {
		return overcontrolname;
	}

	public void setOvercontrolname(String overcontrolname) {
		this.overcontrolname = overcontrolname;
	}

	public String getCanceltypename() {
		return canceltypename;
	}

	public void setCanceltypename(String canceltypename) {
		this.canceltypename = canceltypename;
	}

	public String getIndoorstaffname() {
		return indoorstaffname;
	}

	public void setIndoorstaffname(String indoorstaffname) {
		this.indoorstaffname = indoorstaffname;
	}

	public String getIslastname() {
		return islastname;
	}

	public void setIslastname(String islastname) {
		this.islastname = islastname;
	}

	public String getIschainname() {
		return ischainname;
	}

	public void setIschainname(String ischainname) {
		this.ischainname = ischainname;
	}

	public String getTickettypename() {
		return tickettypename;
	}

	public void setTickettypename(String tickettypename) {
		this.tickettypename = tickettypename;
	}

	public String getIscashname() {
		return iscashname;
	}

	public void setIscashname(String iscashname) {
		this.iscashname = iscashname;
	}

	public String getIslongtermname() {
		return islongtermname;
	}

	public void setIslongtermname(String islongtermname) {
		this.islongtermname = islongtermname;
	}

	public String getCreditratingname() {
		return creditratingname;
	}

	public void setCreditratingname(String creditratingname) {
		this.creditratingname = creditratingname;
	}

}