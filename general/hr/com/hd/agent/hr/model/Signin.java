package com.hd.agent.hr.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Signin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 日期
     */
    private String businessdate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 人员档案编号
     */
    private String personid;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 早上上班打卡时间
     */
    private Date ambegin;

    /**
     * 早上上班签到附件
     */
    private String ambeginfile;
    /**
     * 上午上班打卡坐标X
     */
    private String ambeginx;
    /**
     * 上午上班打卡坐标Y
     */
    private String ambeginy;

    /**
     * 早上下班打卡时间
     */
    private Date amend;

    /**
     * 早上下班签到附件
     */
    private String amendfile;
    /**
     * 上午下班打卡坐标X
     */
    private String amendx;
    /**
     * 上午下班打卡坐标Y
     */
    private String amendy;
    /**
     * 下午上班打卡时间
     */
    private Date pmbegin;

    /**
     * 下午上班签到附件
     */
    private String pmbeginfile;
    /**
     * 下午上班打卡坐标X
     */
    private String pmbeginx;
    /**
     * 下午上班打卡坐标Y
     */
    private String pmbeginy;
    /**
     * 下午下班打卡时间
     */
    private Date pmend;

    /**
     * 下午下班签到附件
     */
    private String pmendfile;
    /**
     * 下午下班打卡坐标X
     */
    private String pmendx;
    /**
     * 下午下班打卡坐标y
     */
    private String pmendy;
    /**
     * 外出打卡时间
     */
    private Date outtime;

    /**
     * 外出附件
     */
    private String outpic;
    /**
     * 外出打卡坐标X
     */
    private String outx;
    /**
     * 外出打卡坐标y
     */
    private String outy;
    /**
     * 制单人用户编号
     */
    private String adduserid;

    /**
     * 制单人用户名称
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
     * 人员名称
     */
    private String personname;
    
    /**
     * 用户名
     */
    private String username;

    /**
     * 部门编号
     */
    private String deptid;

    /**
     * 部门名称
     */
    private String deptname;
    
    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
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
     * @return 人员档案编号
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid 
	 *            人员档案编号
     */
    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

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
     * @return 早上上班打卡时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAmbegin() {
        return ambegin;
    }

    /**
     * @param ambegin 
	 *            早上上班打卡时间
     */
    public void setAmbegin(Date ambegin) {
        this.ambegin = ambegin;
    }

    /**
     * @return 早上上班签到附件
     */
    public String getAmbeginfile() {
        return ambeginfile;
    }

    /**
     * @param ambeginfile 
	 *            早上上班签到附件
     */
    public void setAmbeginfile(String ambeginfile) {
        this.ambeginfile = ambeginfile == null ? null : ambeginfile.trim();
    }

    /**
     * @return 早上下班打卡时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAmend() {
        return amend;
    }

    /**
     * @param amend 
	 *            早上下班打卡时间
     */
    public void setAmend(Date amend) {
        this.amend = amend;
    }

    /**
     * @return 早上下班签到附件
     */
    public String getAmendfile() {
        return amendfile;
    }

    /**
     * @param amendfile 
	 *            早上下班签到附件
     */
    public void setAmendfile(String amendfile) {
        this.amendfile = amendfile == null ? null : amendfile.trim();
    }

    /**
     * @return 下午上班打卡时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPmbegin() {
        return pmbegin;
    }

    /**
     * @param pmbegin 
	 *            下午上班打卡时间
     */
    public void setPmbegin(Date pmbegin) {
        this.pmbegin = pmbegin;
    }

    /**
     * @return 下午上班签到附件
     */
    public String getPmbeginfile() {
        return pmbeginfile;
    }

    /**
     * @param pmbeginfile 
	 *            下午上班签到附件
     */
    public void setPmbeginfile(String pmbeginfile) {
        this.pmbeginfile = pmbeginfile == null ? null : pmbeginfile.trim();
    }

    /**
     * @return 下午下班打卡时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPmend() {
        return pmend;
    }

    /**
     * @param pmend 
	 *            下午下班打卡时间
     */
    public void setPmend(Date pmend) {
        this.pmend = pmend;
    }

    /**
     * @return 下午下班签到附件
     */
    public String getPmendfile() {
        return pmendfile;
    }

    /**
     * @param pmendfile 
	 *            下午下班签到附件
     */
    public void setPmendfile(String pmendfile) {
        this.pmendfile = pmendfile == null ? null : pmendfile.trim();
    }

    /**
     * @return 制单人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人用户名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人用户名称
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

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAmbeginx() {
		return ambeginx;
	}

	public void setAmbeginx(String ambeginx) {
		this.ambeginx = ambeginx;
	}

	public String getAmbeginy() {
		return ambeginy;
	}

	public void setAmbeginy(String ambeginy) {
		this.ambeginy = ambeginy;
	}

	public String getAmendx() {
		return amendx;
	}

	public void setAmendx(String amendx) {
		this.amendx = amendx;
	}

	public String getAmendy() {
		return amendy;
	}

	public void setAmendy(String amendy) {
		this.amendy = amendy;
	}

	public String getPmbeginx() {
		return pmbeginx;
	}

	public void setPmbeginx(String pmbeginx) {
		this.pmbeginx = pmbeginx;
	}

	public String getPmbeginy() {
		return pmbeginy;
	}

	public void setPmbeginy(String pmbeginy) {
		this.pmbeginy = pmbeginy;
	}

	public String getPmendx() {
		return pmendx;
	}

	public void setPmendx(String pmendx) {
		this.pmendx = pmendx;
	}

	public String getPmendy() {
		return pmendy;
	}

	public void setPmendy(String pmendy) {
		this.pmendy = pmendy;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getOuttime() {
		return outtime;
	}

	public void setOuttime(Date outtime) {
		this.outtime = outtime;
	}

	public String getOutpic() {
		return outpic;
	}

	public void setOutpic(String outpic) {
		this.outpic = outpic;
	}

	public String getOutx() {
		return outx;
	}

	public void setOutx(String outx) {
		this.outx = outx;
	}

	public String getOuty() {
		return outy;
	}

	public void setOuty(String outy) {
		this.outy = outy;
	}

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}