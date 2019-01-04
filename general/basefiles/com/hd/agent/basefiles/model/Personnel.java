package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Personnel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String delEduIds;
    private String delWorkIds;
    private String delPostIds;
    private String delCustomerIds;
    private String delBrandIds;
    
    /**
     * 对应客户，对应品牌是否有作修改
     */
    private String iseditlist;
    /**
     * 附件编号
     */
    private String adjunctid;
    
    /**
     * 原先的人员编码
     */
    private String oldId;

	/**
     * 编码数组
     */
    private String idArr;
	
    /**
     * 教育经历删除ID
     */
    private String eduListDelId;
    
    /**
     * 岗位变动删除ID
     */
    private String postListDelId;
    
    /**
     * 工作经历删除ID
     */
    private String workListDelId;
    
    /**
     * 对应客户删除ID
     */
    private String customerListDelId;
    
    /**
     * 对应品牌删除ID
     */
    private String brandListDelId;
    
    /**
     * 对应品牌编码字符串集合
     */
    private String brandids;
    
    /**
     * 对应客户编码字符串集合
     */
    private String customerids;
    
    /**
     * 编码
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 状态4新增/3暂存/2保存/1启用/0禁用
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String stateName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 建档人编号
     */
    private String adduserid;
    
    /**
     * 建档人
     */
    private String adduser;
    
    /**
     * 建档部门
     */
    private String adddept;

    /**
     * 建档部门编号
     */
    private String adddeptid;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;
    
    /**
     * 最后修改人
     */
    private String modifyuser;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 启用人编号
     */
    private String openuserid;
    
    /**
     * 启用人
     */
    private String openuser;

    /**
     * 启用人时间
     */
    private Date opentime;

    /**
     * 禁用人编号
     */
    private String closeuserid;
    
    /**
     * 禁用人
     */
    private String closeuser;

    /**
     * 禁用时间
     */
    private Date closetime;

    /**
     * 照片路径
     */
    private String photograph;

    /**
     * 姓名拼音
     */
    private String namepinyin;

    /**
     * 员工类型3未知/2全职/1兼职
     */
    private String personnelstyle;
    
    /**
     * 员工类型名称
     */
    private String perStyleName;

    /**
     * 最高学历8未知/7小学/6初中/5高中/4大专/3本科/2硕士/1博士
     */
    private String highestdegree;
    
    /**
     * 最高学历名称
     */
    private String highestDegreeName;

    /**
     * 入职时间
     */
    private String datesemployed;

    /**
     * 所属部门编号
     */
    private String belongdeptid;
    
    /**
     * 所属部门
     */
    private String belongdept;

	/**
     * 所属岗位
     */
    private String belongpost;
    
    /**
     * 所属岗位名称
     */
    private String belongpostName;

    /**
     * 性别3未知/2男/1女
     */
    private String sex;
    
    /**
     * 性别名称
     */
    private String sexName;

    /**
     * 婚姻状况3未知/2已婚/1未婚
     */
    private String maritalstatus;
    
    /**
     * 婚姻状况名称
     */
    private String maritalstatusName;

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
     * 民族1汉/3未知/...
     */
    private String nation;
    
    /**
     * 民族名称
     */
    private String nationName;

    /**
     * 籍贯
     */
    private String nativeplace;

    /**
     * 政治面貌5未知/4党员/3团员/2群众/1其他
     */
    private String polstatus;
    
    /**
     * 政治面貌名称
     */
    private String polstatusName;

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
    private String telphone;

    /**
     * 公司短号
     */
    private String compcornet;

    /**
     * 薪酬方案
     */
    private String salaryscheme;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 居住地邮编
     */
    private String addrpostcode;

    /**
     * 户籍地址
     */
    private String householdaddr;

    /**
     * 户籍地邮编
     */
    private String householdcode;

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
     * 业务类型
     */
    private String employetype;
    
    /**
     * 业务属性名称
     */
    private String employetypeName;
    
    /**
     * 上级领导
     */
    private String leadid;
    
    /**
     * 上级领导名称
     */
    private String leadname;

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
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 状态4新增/3暂存/2保存/1启用/0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态4新增/3暂存/2保存/1启用/0禁用
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
     * @return 建档人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 建档时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 启用人编号
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人编号
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
    }

    /**
     * @return 启用人时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getOpentime() {
        return opentime;
    }

    /**
     * @param opentime 
	 *            启用人时间
     */
    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    /**
     * @return 禁用人编号
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人编号
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    /**
     * @return 禁用时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 照片路径
     */
    public String getPhotograph() {
        return photograph;
    }

    /**
     * @param photograph 
	 *            照片路径
     */
    public void setPhotograph(String photograph) {
        this.photograph = photograph == null ? null : photograph.trim();
    }

    /**
     * @return 姓名拼音
     */
    public String getNamepinyin() {
        return namepinyin;
    }

    /**
     * @param namepinyin 
	 *            姓名拼音
     */
    public void setNamepinyin(String namepinyin) {
        this.namepinyin = namepinyin == null ? null : namepinyin.trim();
    }

    /**
     * @return 员工类型3未知/2全职/1兼职
     */
    public String getPersonnelstyle() {
        return personnelstyle;
    }

    /**
     * @param personnelstyle 
	 *            员工类型3未知/2全职/1兼职
     */
    public void setPersonnelstyle(String personnelstyle) {
        this.personnelstyle = personnelstyle == null ? null : personnelstyle.trim();
    }

    /**
     * @return 最高学历8未知/7小学/6初中/5高中/4大专/3本科/2硕士/1博士
     */
    public String getHighestdegree() {
        return highestdegree;
    }

    /**
     * @param highestdegree 
	 *            最高学历8未知/7小学/6初中/5高中/4大专/3本科/2硕士/1博士
     */
    public void setHighestdegree(String highestdegree) {
        this.highestdegree = highestdegree == null ? null : highestdegree.trim();
    }

    /**
     * @return 入职时间
     */
    public String getDatesemployed() {
        return datesemployed;
    }

    /**
     * @param datesemployed 
	 *            入职时间
     */
    public void setDatesemployed(String datesemployed) {
        this.datesemployed = datesemployed;
    }

    /**
     * @return 所属部门编号
     */
    public String getBelongdeptid() {
        return belongdeptid;
    }

    /**
     * @param belongdeptid 
	 *            所属部门编号
     */
    public void setBelongdeptid(String belongdeptid) {
        this.belongdeptid = belongdeptid == null ? null : belongdeptid.trim();
    }

    /**
     * @return 所属岗位
     */
    public String getBelongpost() {
        return belongpost;
    }

    /**
     * @param belongpost 
	 *            所属岗位
     */
    public void setBelongpost(String belongpost) {
        this.belongpost = belongpost == null ? null : belongpost.trim();
    }

    /**
     * @return 性别3未知/2男/1女
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex 
	 *            性别3未知/2男/1女
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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
        this.maritalstatus = maritalstatus == null ? null : maritalstatus.trim();
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
        this.age = age == null ? null : age.trim();
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
        this.idcard = idcard == null ? null : idcard.trim();
    }

    /**
     * @return 民族1汉/3未知/...
     */
    public String getNation() {
        return nation;
    }

    /**
     * @param nation 
	 *            民族1汉/3未知/...
     */
    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
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
        this.nativeplace = nativeplace == null ? null : nativeplace.trim();
    }

    /**
     * @return 政治面貌5未知/4党员/3团员/2群众/1其他
     */
    public String getPolstatus() {
        return polstatus;
    }

    /**
     * @param polstatus 
	 *            政治面貌5未知/4党员/3团员/2群众/1其他
     */
    public void setPolstatus(String polstatus) {
        this.polstatus = polstatus == null ? null : polstatus.trim();
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
        this.tel = tel == null ? null : tel.trim();
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
        this.fax = fax == null ? null : fax.trim();
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
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone 
	 *            手机号码
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * @return 公司短号
     */
    public String getCompcornet() {
        return compcornet;
    }

    /**
     * @param compcornet 
	 *            公司短号
     */
    public void setCompcornet(String compcornet) {
        this.compcornet = compcornet == null ? null : compcornet.trim();
    }

    /**
     * @return 薪酬方案
     */
    public String getSalaryscheme() {
        return salaryscheme;
    }

    /**
     * @param salaryscheme 
	 *            薪酬方案
     */
    public void setSalaryscheme(String salaryscheme) {
        this.salaryscheme = salaryscheme == null ? null : salaryscheme.trim();
    }


    /**
     * @return 居住地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 
	 *            居住地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return 居住地邮编
     */
    public String getAddrpostcode() {
        return addrpostcode;
    }

    /**
     * @param addrpostcode 
	 *            居住地邮编
     */
    public void setAddrpostcode(String addrpostcode) {
        this.addrpostcode = addrpostcode == null ? null : addrpostcode.trim();
    }

    /**
     * @return 户籍地址
     */
    public String getHouseholdaddr() {
        return householdaddr;
    }

    /**
     * @param householdaddr 
	 *            户籍地址
     */
    public void setHouseholdaddr(String householdaddr) {
        this.householdaddr = householdaddr == null ? null : householdaddr.trim();
    }

    /**
     * @return 户籍地邮编
     */
    public String getHouseholdcode() {
        return householdcode;
    }

    /**
     * @param householdcode 
	 *            户籍地邮编
     */
    public void setHouseholdcode(String householdcode) {
        this.householdcode = householdcode == null ? null : householdcode.trim();
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
     * @return 编码数组
     * @author panxiaoxiao 
     * @date 2013-2-1
     */
    public String getIdArr() {
		return idArr;
	}

    /**
     * 编码数组
     * @param idArr
     * @author panxiaoxiao 
     * @date 2013-2-1
     */
	public void setIdArr(String idArr) {
		this.idArr = idArr;
	}
	
	   
    /**
     * @return 原先的人员编码
     * @author panxiaoxiao 
     * @date 2013-2-5
     */
	 public String getOldId() {
		return oldId;
	}

	 /**
	  * 原先的人员编码
	  * @param oldId
	  * @author panxiaoxiao 
	  * @date 2013-2-5
	  */
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getEduListDelId() {
		return eduListDelId;
	}

	public void setEduListDelId(String eduListDelId) {
		this.eduListDelId = eduListDelId;
	}

	public String getPostListDelId() {
		return postListDelId;
	}

	public void setPostListDelId(String postListDelId) {
		this.postListDelId = postListDelId;
	}

	public String getWorkListDelId() {
		return workListDelId;
	}

	public void setWorkListDelId(String workListDelId) {
		this.workListDelId = workListDelId;
	}

	/**
	 * @return 附件编号
	 * @author panxiaoxiao 
	 * @date 2013-3-21
	 */
	public String getAdjunctid() {
		return adjunctid;
	}

	/**
	 * 附件编号
	 * @param adjunctpath 
	 * @author panxiaoxiao 
	 * @date 2013-3-21
	 */
	public void setAdjunctid(String adjunctid) {
		this.adjunctid = adjunctid;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getPerStyleName() {
		return perStyleName;
	}

	public void setPerStyleName(String perStyleName) {
		this.perStyleName = perStyleName;
	}

	public String getHighestDegreeName() {
		return highestDegreeName;
	}

	public void setHighestDegreeName(String highestDegreeName) {
		this.highestDegreeName = highestDegreeName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getMaritalstatusName() {
		return maritalstatusName;
	}

	public void setMaritalstatusName(String maritalstatusName) {
		this.maritalstatusName = maritalstatusName;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getPolstatusName() {
		return polstatusName;
	}

	public void setPolstatusName(String polstatusName) {
		this.polstatusName = polstatusName;
	}
	
	 /**
     * @return 建档人
     */
    public String getAdduser() {
		return adduser;
	}

    /**
     * @param adduser
	 *            建档人
     */
	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}

	/**
     * @return 建档部门
     */
	public String getAdddept() {
		return adddept;
	}

	 /**
     * @param adddept
	 *            建档部门
     */
	public void setAdddept(String adddept) {
		this.adddept = adddept;
	}

	/**
     * @return 最后修改人
     */
	public String getModifyuser() {
		return modifyuser;
	}

	 /**
     * @param modifyuser
	 *            最后修改人
     */
	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	/**
     * @return 最后启用人
     */
	public String getOpenuser() {
		return openuser;
	}

	 /**
     * @param openuser
	 *            最后启用人
     */
	public void setOpenuser(String openuser) {
		this.openuser = openuser;
	}

	/**
     * @return 最后禁用人
     */
	public String getCloseuser() {
		return closeuser;
	}

	 /**
     * @param closeuser
	 *            最后禁用人
     */
	public void setCloseuser(String closeuser) {
		this.closeuser = closeuser;
	}

	/**
     * @return 所属部门
     */
	public String getBelongdept() {
		return belongdept;
	}

	 /**
     * @param belongdept
	 *            所属部门
     */
	public void setBelongdept(String belongdept) {
		this.belongdept = belongdept;
	}

	public String getEmployetype() {
		return employetype;
	}

	public void setEmployetype(String employetype) {
		this.employetype = employetype;
	}

	public String getEmployetypeName() {
		return employetypeName;
	}

	public void setEmployetypeName(String employetypeName) {
		this.employetypeName = employetypeName;
	}

	public String getBelongpostName() {
		return belongpostName;
	}

	public void setBelongpostName(String belongpostName) {
		this.belongpostName = belongpostName;
	}

	public String getDelEduIds() {
		return delEduIds;
	}

	public void setDelEduIds(String delEduIds) {
		this.delEduIds = delEduIds;
	}

	public String getDelWorkIds() {
		return delWorkIds;
	}

	public void setDelWorkIds(String delWorkIds) {
		this.delWorkIds = delWorkIds;
	}

	public String getDelPostIds() {
		return delPostIds;
	}

	public void setDelPostIds(String delPostIds) {
		this.delPostIds = delPostIds;
	}

	public String getDelCustomerIds() {
		return delCustomerIds;
	}

	public void setDelCustomerIds(String delCustomerIds) {
		this.delCustomerIds = delCustomerIds;
	}

	public String getDelBrandIds() {
		return delBrandIds;
	}

	public void setDelBrandIds(String delBrandIds) {
		this.delBrandIds = delBrandIds;
	}

	public String getCustomerListDelId() {
		return customerListDelId;
	}

	public void setCustomerListDelId(String customerListDelId) {
		this.customerListDelId = customerListDelId;
	}

	public String getBrandListDelId() {
		return brandListDelId;
	}

	public void setBrandListDelId(String brandListDelId) {
		this.brandListDelId = brandListDelId;
	}

	public String getBrandids() {
		return brandids;
	}

	public void setBrandids(String brandids) {
		this.brandids = brandids;
	}

	public String getCustomerids() {
		return customerids;
	}

	public void setCustomerids(String customerids) {
		this.customerids = customerids;
	}

	public String getIseditlist() {
		return iseditlist;
	}

	public void setIseditlist(String iseditlist) {
		this.iseditlist = iseditlist;
	}

	public String getLeadid() {
		return leadid;
	}

	public void setLeadid(String leadid) {
		this.leadid = leadid;
	}

	public String getLeadname() {
		return leadname;
	}

	public void setLeadname(String leadname) {
		this.leadname = leadname;
	}
}