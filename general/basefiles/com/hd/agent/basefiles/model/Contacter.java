package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 联系人
 * @author zhengziyong
 *
 */
public class Contacter implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String id;
    
    private String oldid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 状态4新增3暂存2保存1启用0禁用
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 照片
     */
    private String image;

    /**
     * 姓名拼音
     */
    private String spell;

    /**
     * 初次接触日期
     */
    private String firstcall;

    /**
     * 默认联系人分类
     */
    private String linkmansort;

    /**
     * 联系人类型1客户2供应商
     */
    private String type;

    /**
     * 所属客户
     */
    private String customer;
    
    /**
     * 所属客户名称
     */
    private String customername;

    /**
     * 所属供应商
     */
    private String supplier;
    
    /**
     * 所属供应商名称
     */
    private String suppliername;

    /**
     * 职务名称
     */
    private String job;

    /**
     * 性别2未知/1男/0女
     */
    private String sex;

    /**
     * 婚姻状况3未知/2已婚/1未婚
     */
    private String maritalstatus;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 年龄
     */
    private String age;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 民族(根据国家标准获取)
     */
    private String nation;

    /**
     * 籍贯
     */
    private String nativeplace;
    
    /**
     * 政治面貌
     */
    private String polstatus;

    /**
     * 电话
     */
    private String tel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * MSN号码
     */
    private String msn;

    /**
     * 最佳联系时间
     */
    private String bestcall;

    /**
     * 最近联系时间
     */
    private String newcalldate;

    /**
     * 居住地址
     */
    private String liveaddr;

    /**
     * 居住地邮编
     */
    private String livezip;

    /**
     * 户籍地址
     */
    private String nativeaddr;

    /**
     * 户籍地邮编
     */
    private String nativezip;
    
    /**
     * 家庭状况
     */
    private String family;

    /**
     * 兴趣爱好
     */
    private String hobby;

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
    
    private String isdefault;
    
    private ContacterAndSort contacterAndSort;
    
    private List<ContacterAndSort> contacterAndSortList;

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
        this.id = id;
    }

    /**
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            姓名
     */
    public void setName(String name) {
        this.name = name;
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
     * @return 照片
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image 
	 *            照片
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return 姓名拼音
     */
    public String getSpell() {
        return spell;
    }

    /**
     * @param spell 
	 *            姓名拼音
     */
    public void setSpell(String spell) {
        this.spell = spell;
    }

    /**
     * @return 初次接触日期
     */
    public String getFirstcall() {
        return firstcall;
    }

    /**
     * @param firstcall 
	 *            初次接触日期
     */
    public void setFirstcall(String firstcall) {
        this.firstcall = firstcall;
    }

    /**
     * @return 默认联系人分类
     */
    public String getLinkmansort() {
        return linkmansort;
    }

    /**
     * @param linkmansort 
	 *            默认联系人分类
     */
    public void setLinkmansort(String linkmansort) {
        this.linkmansort = linkmansort;
    }

    /**
     * @return 联系人类型1客户2供应商
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            联系人类型1客户2供应商
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return 所属客户
     */
    public String getCustomer() {
		return customer;
	}

    /**
     * @param customer 
	 *            所属客户
     */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
     * @return 所属供应商
     */
	public String getSupplier() {
		return supplier;
	}

	/**
     * @param supplier 
	 *            所属供应商
     */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
     * @return 职务名称
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job 
	 *            职务名称
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * @return 性别2未知/1男/0女
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex 
	 *            性别2未知/1男/0女
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return 婚姻状况3未知/2已婚/1未婚
     */
    public String getMaritalstatus() {
        return maritalstatus;
    }

    /**
     * @param maritalstatus 
	 *            婚姻状况3未知/2已婚/1未婚
     */
    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    /**
     * @return 出生日期
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday 
	 *            出生日期
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return 年龄
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age 
	 *            年龄
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return 身份证号码
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard 
	 *            身份证号码
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * @return 民族(根据国家标准获取)
     */
    public String getNation() {
        return nation;
    }

    /**
     * @param nation 
	 *            民族(根据国家标准获取)
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * @return 籍贯
     */
    public String getNativeplace() {
        return nativeplace;
    }

    /**
     * @param nativeplace 
	 *            籍贯
     */
    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    /**
     * 政治面貌
     * @return
     * @author zhengziyong 
     * @date Apr 18, 2013
     */
    public String getPolstatus() {
		return polstatus;
	}

    /**
     * 政治面貌
     * @param polstatus
     * @author zhengziyong 
     * @date Apr 18, 2013
     */
	public void setPolstatus(String polstatus) {
		this.polstatus = polstatus;
	}

	/**
     * @return 电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel 
	 *            电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax 
	 *            传真
     */
    public void setFax(String fax) {
        this.fax = fax;
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
     * @return QQ号码
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq 
	 *            QQ号码
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * @return MSN号码
     */
    public String getMsn() {
        return msn;
    }

    /**
     * @param msn 
	 *            MSN号码
     */
    public void setMsn(String msn) {
        this.msn = msn;
    }

    /**
     * @return 最佳联系时间
     */
    public String getBestcall() {
        return bestcall;
    }

    /**
     * @param bestcall 
	 *            最佳联系时间
     */
    public void setBestcall(String bestcall) {
        this.bestcall = bestcall;
    }

    /**
     * @return 最近联系时间
     */
    public String getNewcalldate() {
        return newcalldate;
    }

    /**
     * @param newcalldate 
	 *            最近联系时间
     */
    public void setNewcalldate(String newcalldate) {
        this.newcalldate = newcalldate;
    }

    /**
     * @return 居住地址
     */
    public String getLiveaddr() {
        return liveaddr;
    }

    /**
     * @param liveaddr 
	 *            居住地址
     */
    public void setLiveaddr(String liveaddr) {
        this.liveaddr = liveaddr;
    }

    /**
     * @return 居住地邮编
     */
    public String getLivezip() {
        return livezip;
    }

    /**
     * @param livezip 
	 *            居住地邮编
     */
    public void setLivezip(String livezip) {
        this.livezip = livezip;
    }

    /**
     * @return 户籍地址
     */
    public String getNativeaddr() {
        return nativeaddr;
    }

    /**
     * @param nativeaddr 
	 *            户籍地址
     */
    public void setNativeaddr(String nativeaddr) {
        this.nativeaddr = nativeaddr;
    }

    /**
     * @return 户籍地邮编
     */
    public String getNativezip() {
        return nativezip;
    }

    /**
     * @param nativezip 
	 *            户籍地邮编
     */
    public void setNativezip(String nativezip) {
        this.nativezip = nativezip;
    }
    
    /**
     * @return 家庭状况
     */
    public String getFamily() {
        return family;
    }

    /**
     * @param family 
	 *            家庭状况
     */
    public void setFamily(String family) {
        this.family = family;
    }

    /**
     * @return 兴趣爱好
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby 
	 *            兴趣爱好
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
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
        this.adduserid = adduserid;
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
        this.addusername = addusername;
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
        this.adddeptid = adddeptid;
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
        this.modifyuserid = modifyuserid;
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
        this.modifyusername = modifyusername;
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
        this.openuserid = openuserid;
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
        this.closeuserid = closeuserid;
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

	public String getOldid() {
		return oldid;
	}

	public void setOldid(String oldid) {
		this.oldid = oldid;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public ContacterAndSort getContacterAndSort() {
		return contacterAndSort;
	}

	public void setContacterAndSort(ContacterAndSort contacterAndSort) {
		this.contacterAndSort = contacterAndSort;
	}

	public List<ContacterAndSort> getContacterAndSortList() {
		return contacterAndSortList;
	}

	public void setContacterAndSortList(List<ContacterAndSort> contacterAndSortList) {
		this.contacterAndSortList = contacterAndSortList;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
}