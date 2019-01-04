package com.hd.agent.common.model;

import java.io.Serializable;
import java.util.Date;

public class AttachFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 全路径
     */
    private String fullpath;
    /**
     * 转换后的pdf文件路径
     */
    private String pdfpath;
    /**
     * 转换后的html文件路径
     */
    private String htmlpath;
    /**
     * 转换后的swf文件路径
     */
    private String swfpath;
    /**
     * 旧名称
     */
    private String oldfilename;

    /**
     * 扩展名
     */
    private String ext;

    /**
     * 添加时间
     */
    private Date adddate;

    /**
     * 按部门查
     */
    private String opdept;

    /**
     * 按角色查
     */
    private String oprule;

    /**
     * 按人员查
     */
    private String opuser;
    /**
     * 是否需要转换，1需要转换，0不需要
     */
    private String isconvert;
    /**
     * 转换次数
     */
    private Integer iconverts;
    /**
     *  转换时间
     */
    private Date convertdate;
    
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
        this.id = id;
    }

    /**
     * @return 文件名
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename 
	 *            文件名
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return 全路径
     */
    public String getFullpath() {
        return fullpath;
    }

    /**
     * @param fullpath 
	 *            全路径
     */
    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    /**
     * @return 旧名称
     */
    public String getOldfilename() {
        return oldfilename;
    }

    /**
     * @param oldfilename 
	 *            旧名称
     */
    public void setOldfilename(String oldfilename) {
        this.oldfilename = oldfilename;
    }

    /**
     * @return 扩展名
     */
    public String getExt() {
        return ext;
    }

    /**
     * @param ext 
	 *            扩展名
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * @return 添加时间
     */
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            添加时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }
    

    /**
     * @return 按部门查
     */
    public String getOpdept() {
        return opdept;
    }

    /**
     * @param opdept 
	 *            按部门查
     */
    public void setOpdept(String opdept) {
        this.opdept = opdept;
    }

    /**
     * @return 按角色查
     */
    public String getOprule() {
        return oprule;
    }

    /**
     * @param oprule 
	 *            按角色查
     */
    public void setOprule(String oprule) {
        this.oprule = oprule;
    }

    /**
     * @return 按人员查
     */
    public String getOpuser() {
        return opuser;
    }

    /**
     * @param opuser 
	 *            按人员查
     */
    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }

	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}

	public String getHtmlpath() {
		return htmlpath;
	}

	public void setHtmlpath(String htmlpath) {
		this.htmlpath = htmlpath;
	}

	public String getSwfpath() {
		return swfpath;
	}

	public void setSwfpath(String swfpath) {
		this.swfpath = swfpath;
	}
	/**
	 * 是否需要转换，1需要转换，0不需要
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public String getIsconvert() {
		return isconvert;
	}

	/**
	 * 是否需要转换，1需要转换，0不需要
	 * @param isconvert
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public void setIscovert(String isconvert) {
		this.isconvert = isconvert;
	}

	/**
	 * 转换次数
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public Integer getIconverts() {
		return iconverts;
	}

	/**
	 * 转换次数 
	 * @param iconverts
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public void setIcoverts(Integer iconverts) {
		this.iconverts = iconverts;
	}

	/**
	 * 转换日期
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public Date getConvertdate() {
		return convertdate;
	}

	/**
	 * 转换日期
	 * @param convertdate
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public void setConvertdate(Date convertdate) {
		this.convertdate = convertdate;
	}
    
}