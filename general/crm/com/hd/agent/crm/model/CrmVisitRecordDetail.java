package com.hd.agent.crm.model;

import java.io.Serializable;
/**
 * 客户拜访记录明细
 * @author master
 *
 */
public class CrmVisitRecordDetail implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2208744849423623908L;

	/**
     * 主键
     */
    private Integer id;
    private String fileid;
    /**
     * 单据编号
     */
    private String billid;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 图片路径
     */
    private String imgsrc;

    /**
     * 是否合格0未检查1合格2不合格
     */
    private String isqa;

    /**
     * 陈列标准级别
     */
    private String standard;
    /**
     * 陈列标准级别名称
     */
    private String standardname;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * GPS坐标
     */
    private String gps;

    /**
     * 拍照时间
     */
    private String ptime;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 品牌编号
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编号
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }
    /**
     * @return 品牌名称
     */
    public String getBrandname() {
        return brandname;
    }

    /**
     * @param brandid 
	 *            品牌名称
     */
    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    /**
     * @return 图片路径
     */
    public String getImgsrc() {
        return imgsrc;
    }

    /**
     * @param imgsrc 
	 *            图片路径
     */
    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc == null ? null : imgsrc.trim();
    }

    /**
     * @return 是否合格0未检查1合格2不合格
     */
    public String getIsqa() {
        return isqa;
    }

    /**
     * @param isqa 
	 *            是否合格0未检查1合格2不合格
     */
    public void setIsqa(String isqa) {
        this.isqa = isqa == null ? null : isqa.trim();
    }

    /**
     * @return 陈列标准级别
     */
    public String getStandard() {
        return standard;
    }

    /**
     * @param standard 
	 *            陈列标准级别
     */
    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }

    /**
     * @return 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return GPS坐标
     */
    public String getGps() {
        return gps;
    }

    /**
     * @param gps 
	 *            GPS坐标
     */
    public void setGps(String gps) {
        this.gps = gps == null ? null : gps.trim();
    }

    /**
     * @return 拍照时间
     */
    public String getPtime() {
        return ptime;
    }

    /**
     * @param ptime 
	 *            拍照时间
     */
    public void setPtime(String ptime) {
        this.ptime = ptime == null ? null : ptime.trim();
    }

	public String getStandardname() {
		return standardname;
	}

	public void setStandardname(String standardname) {
		this.standardname = standardname;
	}

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }
}