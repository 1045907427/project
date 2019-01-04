package com.hd.agent.agprint.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class PrintPaperSize implements Serializable {

    private static final long serialVersionUID = -6499021569938444141L;
    /**
     * 编号
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 长度
     */
    private Double width;
    /**
     * 宽度
     */
    private Double height;
    /**
     * 长度
     */
    private Integer iwidth;

    /**
     * 状态：0禁用，1启用
     */
    private String state;

    /**
     * 显示顺序
     */
    private Integer seq;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加用户编号
     */
    private String adduserid;

    /**
     * 添加用户名称
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    private String modifyuserid;

    /**
     * 修改用户名称
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
        this.id = id == null ? null : id.trim();
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
     * @return 长
     */
    public Double getWidth() {
        return width;
    }

    /**
     * @param width 
	 *            长
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    /**
     * @return 高
     */
    public Double getHeight() {
        return height;
    }

    /**
     * @param height 
	 *            高
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * @return 状态：0禁用，1启用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态：0禁用，1启用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 显示顺序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            显示顺序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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
     * @return 添加用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加用户名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加用户名称
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
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

    public String getModifyuserid() {
        return modifyuserid;
    }

    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 修改用户名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改用户名称
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
     * 转以0.1为精度的长度
     * @return
     */
    public Integer getIntwidth(){
        int iwidth=0;
        if(width!=null){
            iwidth=(int)(width*100);
        }
        return iwidth;
    }
    /**
     * 转以0.1为精度的宽度
     * @return
     */
    public Integer getIntheight(){
        int iheight=0;
        if(height!=null){
            iheight=(int)(height*100);
        }
        return iheight;
    }

}