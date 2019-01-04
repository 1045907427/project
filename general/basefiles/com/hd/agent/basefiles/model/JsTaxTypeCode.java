package com.hd.agent.basefiles.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class JsTaxTypeCode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 编码-篇
     */
    private String idpart;
    /**
     * 编码-类
     */
    private String idcategory;
    /**
     * 编码-章
     */
    private String idchapter;
    /**
     * 编码-节
     */
    private String idsection;
    /**
     * 编码-条
     */
    private String idarticle;
    /**
     * 编码-款
     */
    private String idparagraph;
    /**
     * 编码-项
     */
    private String idsubparagraph;
    /**
     * 编码-目
     */
    private String iditem;
    /**
     * 编码-子目
     */
    private String idsubitem;
    /**
     * 编码-细目
     */
    private String iddetailitem;

    /**
     * 合并编码
     */
    private String mergeid;

    /**
     * 名称
     */
    private String goodsname;

    /**
     * 货物和劳务名称
     */
    private String description;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 制单人用户编号
     */
    private String adduserid;

    /**
     * 制单人用户名称
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
     * @return 合并编码
     */
    public String getMergeid() {
        return mergeid;
    }

    /**
     * @param mergeid 
	 *            合并编码
     */
    public void setMergeid(String mergeid) {
        this.mergeid = mergeid == null ? null : mergeid.trim();
    }

    /**
     * @return 名称
     */
    public String getGoodsname() {
        return goodsname;
    }

    /**
     * @param goodsname 
	 *            名称
     */
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }

    /**
     * @return 货物和劳务名称
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            货物和劳务名称
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword 
	 *            关键词
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getIdpart() {
        return idpart;
    }

    public void setIdpart(String idpart) {
        this.idpart = idpart == null ? null : idpart.trim();
    }

    public String getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory == null ? null : idcategory.trim();
    }

    public String getIdchapter() {
        return idchapter;
    }

    public void setIdchapter(String idchapter) {
        this.idchapter = idchapter == null ? null : idchapter.trim();
    }

    public String getIdsection() {
        return idsection;
    }

    public void setIdsection(String idsection) {
        this.idsection = idsection == null ? null : idsection.trim();
    }

    public String getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(String idarticle) {
        this.idarticle = idarticle == null ? null : idarticle.trim();
    }

    public String getIdparagraph() {
        return idparagraph;
    }

    public void setIdparagraph(String idparagraph) {
        this.idparagraph = idparagraph == null ? null : idparagraph.trim();
    }

    public String getIdsubparagraph() {
        return idsubparagraph;
    }

    public void setIdsubparagraph(String idsubparagraph) {
        this.idsubparagraph = idsubparagraph == null ? null : idsubparagraph.trim();
    }

    public String getIditem() {
        return iditem;
    }

    public void setIditem(String iditem) {
        this.iditem = iditem == null ? null : iditem.trim();
    }

    public String getIdsubitem() {
        return idsubitem;
    }

    public void setIdsubitem(String idsubitem) {
        this.idsubitem = idsubitem == null ? null : idsubitem.trim();
    }

    public String getIddetailitem() {
        return iddetailitem;
    }

    public void setIddetailitem(String iddetailitem) {
        this.iddetailitem = iddetailitem == null ? null : iddetailitem.trim();
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

}