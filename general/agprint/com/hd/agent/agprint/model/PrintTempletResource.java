package com.hd.agent.agprint.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class PrintTempletResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3941646839782971436L;

	/**
     * 编码
     */
    private String id;
    /**
     * 资源编号,非主键
     */
    private Integer viewid;

    /**
     * 代码
     */
    private String code;
    /**
     * 代码名称
     */
    private String codename;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态，1启用，0未启用
     */
    private String state;

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
     * 是否预置，1预置，0否
     */
    private String issystem;
    /**
     * 纸张编号
     */
    private String papersizeid;
    /**
     * 纸张名称
     */
    private String papersizename;
    /**
     * 打印模板代码信息
     */
    private PrintTempletSubject printTempletSubjectInfo;
    /**
     * 纸张信息
     */
    private PrintPaperSize printPaperSizeInfo;

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
        this.name = name == null ? null : name.trim();
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
     * @return 状态，1启用，0未启用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态，1启用，0未启用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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

	public String getIssystem() {
		return issystem;
	}

	public void setIssystem(String issystem) {
		this.issystem = issystem == null ? null : issystem.trim();
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public Integer getViewid() {
		return viewid;
	}

	public void setViewid(Integer viewid) {
		this.viewid = viewid;
	}

    public String getPapersizeid() {
        return papersizeid;
    }

    public void setPapersizeid(String papersizeid) {
        this.papersizeid = papersizeid == null ? null : papersizeid.trim();
    }

    public String getPapersizename() {
        return papersizename;
    }

    public void setPapersizename(String papersizename) {
        this.papersizename = papersizename == null ? null : papersizename.trim();
    }

    public PrintTempletSubject getPrintTempletSubjectInfo() {
        return printTempletSubjectInfo;
    }

    public void setPrintTempletSubjectInfo(PrintTempletSubject printTempletSubjectInfo) {
        this.printTempletSubjectInfo = printTempletSubjectInfo;
    }

    public PrintPaperSize getPrintPaperSizeInfo() {
        return printPaperSizeInfo;
    }

    public void setPrintPaperSizeInfo(PrintPaperSize printPaperSizeInfo) {
        this.printPaperSizeInfo = printPaperSizeInfo;
    }
}