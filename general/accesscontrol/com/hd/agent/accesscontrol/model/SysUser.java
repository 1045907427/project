/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统用户实体类
 * @author chenwei
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    
    /**
     * 新密码
     */
    private String newpassword;
    
    /**
     * 人员编号
     */
    private String personnelid;
    /**
     * 工作岗位编号
     */
    private String workjobid;
    
    /**
     * 工作岗位名称
     */
    private String workjobName;
    /**
     * 排序
     */
    private String seq;
    
    /**
     * 是否允许修改密码
     */
    private String ispwd;
    
   /**
    * 是否允许修改密码名称
    */
    private String ispwdname;
    
    /**
     * 是否手机用户
     */
    private String isphone;
    
    /**
     * 是否手机用户名称
     */
    private String isphonename;
    /**
     * 登录的状态1网页登录2手机登录3客户端登陆
     */
    private String loginType;
    /**
     * 是否上传手机定位1是0否
     */
    private String isuploadlocation;
    /**
     * 上次登录IP地址
     */
    private String lastip;
    /**
     * 上次登录mac地址
     */
    private String lastmac;
    /**
     * 上次手机登录sid
     */
    private String lastsid;
    /**
     * 手机是否登录1是0否
     */
    private String isPhoneLogin;
    /**
     * 系统是否登录 1是0否
     */
    private String isSysLogin;
    /**
     * 个推cid（客户端编号）
     */
    private String cid;
    /**
     * @return 人员编号
     * @author panxiaoxiao 
     * @date 2013-3-28
     */
    public String getPersonnelid() {
		return personnelid;
	}

    /**
     * 人员编号
     * @param personnelid
     * @author panxiaoxiao 
     * @date 2013-3-28
     */
	public void setPersonnelid(String personnelid) {
		this.personnelid = personnelid;
	}

	/**
     * @return 新密码
     * @author panxiaoxiao 
     * @date 2013-3-28
     */
    public String getNewpassword() {
		return newpassword;
	}

    /**
     * 新密码
     * @param newpassword
     * @author panxiaoxiao 
     * @date 2013-3-28
     */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	/**
     * 姓名
     */
    private String name;

    /**
     * 性别1:男2:女0:未知
     */
    private String sex;
    
    /**
     * 性别名称
     */
    private String sexName;

    /**
     * 出生日期(1990-10-01)
     */
    private String birthday;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 手机号码
     */
    private String mobilephone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * MSN
     */
    private String msn;

    /**
     * icq号码
     */
    private String icq;

    /**
     * 住址
     */
    private String addr;

    /**
     * 邮编
     */
    private String zip;

    /**
     * 家庭电话
     */
    private String hometelphone;

    /**
     * 头像地址
     */
    private String image;

    /**
     * 状态1有效0无效
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String stateName;

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
     * 部门编号
     */
    private String departmentid;
    /**
     * 部门名称
     */
    private String departmentname;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * 员工级别：0普通员工1部门领导2总经理
     */
    private String leavel;
    /**
     * @return 用户编号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            用户编号
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 
	 *            用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 
	 *            密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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
     * @return 性别1:男2:女0:未知
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex 
	 *            性别1:男2:女0:未知
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return 出生日期(1990-10-01)
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday 
	 *            出生日期(1990-10-01)
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
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
     * @return 手机号码
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * @param mobilephone 
	 *            手机号码
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    /**
     * @return 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 
	 *            电子邮件
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * @return MSN
     */
    public String getMsn() {
        return msn;
    }

    /**
     * @param msn 
	 *            MSN
     */
    public void setMsn(String msn) {
        this.msn = msn == null ? null : msn.trim();
    }

    /**
     * @return icq号码
     */
    public String getIcq() {
        return icq;
    }

    /**
     * @param icq 
	 *            icq号码
     */
    public void setIcq(String icq) {
        this.icq = icq == null ? null : icq.trim();
    }

    /**
     * @return 住址
     */
    public String getAddr() {
        return addr;
    }

    /**
     * @param addr 
	 *            住址
     */
    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
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
     * @return 家庭电话
     */
    public String getHometelphone() {
        return hometelphone;
    }

    /**
     * @param hometelphone 
	 *            家庭电话
     */
    public void setHometelphone(String hometelphone) {
        this.hometelphone = hometelphone == null ? null : hometelphone.trim();
    }

    /**
     * @return 头像地址
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image 
	 *            头像地址
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * @return 状态1有效0无效
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1有效0无效
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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

    /**
     * @return 部门编号
     */
    public String getDepartmentid() {
        return departmentid;
    }

    /**
     * @param departmentid 
	 *            部门编号
     */
    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid == null ? null : departmentid.trim();
    }

	public String getLeavel() {
		return leavel;
	}

	public void setLeavel(String leavel) {
		this.leavel = leavel;
	}

	/**
	 * 
	 * @return 部门名称
	 * @author panxiaoxiao 
	 * @date 2013-3-8
	 */
	public String getDepartmentname() {
		return departmentname;
	}

	/**
     * @param departmentname 
	 *            部门名称
     */
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getWorkjobid() {
		return workjobid;
	}

	public void setWorkjobid(String workjobid) {
		this.workjobid = workjobid;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getWorkjobName() {
		return workjobName;
	}

	public void setWorkjobName(String workjobName) {
		this.workjobName = workjobName;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getIspwd() {
		return ispwd;
	}

	public void setIspwd(String ispwd) {
		this.ispwd = ispwd;
	}

	public String getIsphone() {
		return isphone;
	}

	public void setIsphone(String isphone) {
		this.isphone = isphone;
	}

	public String getIspwdname() {
		return ispwdname;
	}

	public void setIspwdname(String ispwdname) {
		this.ispwdname = ispwdname;
	}

	public String getIsphonename() {
		return isphonename;
	}

	public void setIsphonename(String isphonename) {
		this.isphonename = isphonename;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getIsuploadlocation() {
		return isuploadlocation;
	}

	public void setIsuploadlocation(String isuploadlocation) {
		this.isuploadlocation = isuploadlocation;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public String getLastmac() {
		return lastmac;
	}

	public void setLastmac(String lastmac) {
		this.lastmac = lastmac;
	}

	public String getLastsid() {
		return lastsid;
	}

	public void setLastsid(String lastsid) {
		this.lastsid = lastsid;
	}

	public String getIsPhoneLogin() {
		return isPhoneLogin;
	}

	public void setIsPhoneLogin(String isPhoneLogin) {
		this.isPhoneLogin = isPhoneLogin;
	}

	public String getIsSysLogin() {
		return isSysLogin;
	}

	public void setIsSysLogin(String isSysLogin) {
		this.isSysLogin = isSysLogin;
	}

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    //客户端登陆的部门编号
    private  String deptid;

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
}