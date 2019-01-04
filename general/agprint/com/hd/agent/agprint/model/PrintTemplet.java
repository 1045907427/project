package com.hd.agent.agprint.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class PrintTemplet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3766537086865541194L;

	/**
     * 编码
     */
    private Integer id;

    /**
     * 代码
     */
    private String code;
    /**
     * 代码名称
     */
    private String codename;

    /**
     * 描述名称
     */
    private String name;

    /**
     * 状态：1启用，0禁用
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加者编号
     */
    private String adduserid;

    /**
     * 添加者名称
     */
    private String addusername;

    /**
     * 制单时间
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

    /**
     * 源文件对应t_ge_file中编号
     */
    private String sourcefileid;

    /**
     * 源文件名称
     */
    private String sourcefile;

    /**
     * 源文件路径
     */
    private String sourcepath;

    /**
     * 模板文件对应t_ge_file中编号
     */
    private String templetfileid;

    /**
     * 模板文件名称
     */
    private String templetfile;

    /**
     * 模板文件路径
     */
    private String templetpath;

    /**
     * 所属部门
     */
    private String deptid;

    /**
     * 所属部门名称
     */
    private String deptname;

    /**
     * 是否默认,1默认，0否
     */
    private String isdefault;

    /**
     * 是否预置，1预置，0否
     */
    private String issystem;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 关联类型
     */
    private String linktype;
    /**
     * 关联数据
     */
    private String linkdata;
    /**
     * 关联数据名称
     */
    private String linkdataname;
    /**
     * 标识
     */
    private String mark;
    /**
     * 打印资源
     */
    private String tplresourceid;
    /**
     * 打印模板资源名称
     */
    private String tplresourcename;
    /**
     * 明细数据内容排序
     */
    private String tplorderseqid;
    /**
     * 明细数据内容排序
     */
    private String tplorderseqname;
    /**
     * 纸张大小编号
     */
    private String papersizeid;
    /**
     * 纸张大小名称
     */
    private String papersizename;
    /**
     * 打印模板代码信息
     */
    private PrintTempletSubject printTempletSubjectInfo;
    
    /**
     * 打印模板资源
     */
    private PrintTempletResource printTempletResourceInfo;
    /**
     * 打印内容顺序策略
     */
    private PrintOrderSeq printOrderSeqInfo;
    /**
     * 打印纸张大小
     */
    private PrintPaperSize printPaperSizeInfo;
    /**
     * 每页条数
     */
    private Integer countperpage;
    /**
     * 公司抬头
     */
    private String companytitle;
    /**
     * 是否填充空行
     */
    private String isfillblank;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 销售区域
     */
    private String salesarea;
    /**
     * 销售区域名称
     */
    private String salesareaname;
    /**
     * lodop超文本模板，1普通模式，2图形模式
     */
    private String lodophtmlmodel;
    /**
     * @return 编码
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 
	 *            代码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return 描述名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            描述名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 状态：1启用，0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态：1启用，0禁用
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
     * @return 添加者编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加者编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加者名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加者名称
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
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
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 源文件对应t_ge_file中编号
     */
    public String getSourcefileid() {
        return sourcefileid;
    }

    /**
     * @param sourcefileid 
	 *            源文件对应t_ge_file中编号
     */
    public void setSourcefileid(String sourcefileid) {
        this.sourcefileid = sourcefileid == null ? null : sourcefileid.trim();
    }

    /**
     * @return 源文件名称
     */
    public String getSourcefile() {
        return sourcefile;
    }

    /**
     * @param sourcefile 
	 *            源文件名称
     */
    public void setSourcefile(String sourcefile) {
        this.sourcefile = sourcefile == null ? null : sourcefile.trim();
    }

    /**
     * @return 源文件路径
     */
    public String getSourcepath() {
        return sourcepath;
    }

    /**
     * @param sourcepath 
	 *            源文件路径
     */
    public void setSourcepath(String sourcepath) {
        this.sourcepath = sourcepath == null ? null : sourcepath.trim();
    }

    /**
     * @return 模板文件对应t_ge_file中编号
     */
    public String getTempletfileid() {
        return templetfileid;
    }

    /**
     * @param templetfileid 
	 *            模板文件对应t_ge_file中编号
     */
    public void setTempletfileid(String templetfileid) {
        this.templetfileid = templetfileid == null ? null : templetfileid.trim();
    }

    /**
     * @return 模板文件名称
     */
    public String getTempletfile() {
        return templetfile;
    }

    /**
     * @param templetfile 
	 *            模板文件名称
     */
    public void setTempletfile(String templetfile) {
        this.templetfile = templetfile == null ? null : templetfile.trim();
    }

    /**
     * @return 模板文件路径
     */
    public String getTempletpath() {
        return templetpath;
    }

    /**
     * @param templetpath 
	 *            模板文件路径
     */
    public void setTempletpath(String templetpath) {
        this.templetpath = templetpath == null ? null : templetpath.trim();
    }

    /**
     * @return 所属部门
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            所属部门
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 所属部门名称
     */
    public String getDeptname() {
        return deptname;
    }

    /**
     * @param deptname 
	 *            所属部门名称
     */
    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    /**
     * @return 是否默认,1默认，0否
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault 
	 *            是否默认,1默认，0否
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault == null ? null : isdefault.trim();
    }

    /**
     * @return 是否预置，1预置，0否
     */
    public String getIssystem() {
        return issystem;
    }

    /**
     * @param issystem 
	 *            是否预置，1预置，0否
     */
    public void setIssystem(String issystem) {
        this.issystem = issystem == null ? null : issystem.trim();
    }

    /**
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * @return 关联类型
     */
    public String getLinktype() {
        return linktype;
    }

    /**
     * @param linktype 
	 *            关联类型
     */
    public void setLinktype(String linktype) {
        this.linktype = linktype == null ? null : linktype.trim();
    }

	public String getLinkdata() {
		return linkdata;
	}

	public void setLinkdata(String linkdata) {
        this.linkdata = linkdata == null ? null : linkdata.trim();
	}

	public String getLinkdataname() {
		return linkdataname;
	}

	public void setLinkdataname(String linkdataname) {
        this.linkdataname = linkdataname == null ? null : linkdataname.trim();
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
	}

	public String getTplresourceid() {
		return tplresourceid;
	}

	public void setTplresourceid(String tplresourceid) {
        this.tplresourceid = tplresourceid == null ? null : tplresourceid.trim();
	}

	public String getTplorderseqid() {
		return tplorderseqid;
	}

	public void setTplorderseqid(String tplorderseqid) {
        this.tplorderseqid = tplorderseqid == null ? null : tplorderseqid.trim();
	}

	public String getTplorderseqname() {
		return tplorderseqname;
	}

	public void setTplorderseqname(String tplorderseqname) {
        this.tplorderseqname = tplorderseqname == null ? null : tplorderseqname.trim();
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
        this.codename = codename == null ? null : codename.trim();
	}

	public String getTplresourcename() {
		return tplresourcename;
	}

	public void setTplresourcename(String tplresourcename) {
        this.tplresourcename = tplresourcename == null ? null : tplresourcename.trim();
	}

	public PrintTempletSubject getPrintTempletSubjectInfo() {
		return printTempletSubjectInfo;
	}

	public void setPrintTempletSubjectInfo(PrintTempletSubject printTempletSubjectInfo) {
		this.printTempletSubjectInfo = printTempletSubjectInfo;
	}

	public PrintTempletResource getPrintTempletResourceInfo() {
		return printTempletResourceInfo;
	}

	public void setPrintTempletResourceInfo(PrintTempletResource printTempletResourceInfo) {
		this.printTempletResourceInfo = printTempletResourceInfo;
	}

	public PrintOrderSeq getPrintOrderSeqInfo() {
		return printOrderSeqInfo;
	}

	public void setPrintOrderSeqInfo(PrintOrderSeq printOrderSeqInfo) {
		this.printOrderSeqInfo = printOrderSeqInfo;
	}

	public Integer getCountperpage() {
		return countperpage;
	}

	public void setCountperpage(Integer countperpage) {
		this.countperpage = countperpage;
	}

    public String getPapersizeid() {
        return papersizeid;
    }

    public void setPapersizeid(String papersizeid) {
        this.papersizeid = papersizeid == null ? null : papersizeid.trim();
    }

    public PrintPaperSize getPrintPaperSizeInfo() {
        return printPaperSizeInfo;
    }

    public void setPrintPaperSizeInfo(PrintPaperSize printPaperSizeInfo) {
        this.printPaperSizeInfo = printPaperSizeInfo;
    }

    public String getPapersizename() {
        return papersizename;
    }

    public void setPapersizename(String papersizename) {
        this.papersizename = papersizename == null ? null : papersizename.trim();
    }

    public String getCompanytitle() {
        return companytitle;
    }

    public void setCompanytitle(String companytitle) {
        this.companytitle = companytitle == null ? null : companytitle.trim();
    }

    public String getIsfillblank() {
        return isfillblank;
    }

    public void setIsfillblank(String isfillblank) {
        this.isfillblank = isfillblank == null ? null : isfillblank.trim();
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getSalesarea() {
        return salesarea;
    }

    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    public String getSalesareaname() {
        return salesareaname;
    }

    public void setSalesareaname(String salesareaname) {
        this.salesareaname = salesareaname == null ? null : salesareaname.trim();
    }
    public String getLodophtmlmodel() {
        return lodophtmlmodel;
    }

    public void setLodophtmlmodel(String lodophtmlmodel) {
        this.lodophtmlmodel = lodophtmlmodel == null ? null : lodophtmlmodel.trim();
    }
}