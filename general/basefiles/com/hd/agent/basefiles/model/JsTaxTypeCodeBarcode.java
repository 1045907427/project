package com.hd.agent.basefiles.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class JsTaxTypeCodeBarcode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 税收分类编码
     */
    private String jstypeid;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 添加人
     */
    private String addusername;

    /**
     * 添加人编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 修改人
     */
    private String modifyuserid;

    /**
     * 修改人名称
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
     * @return 税收分类编码
     */
    public String getJstypeid() {
        return jstypeid;
    }

    /**
     * @param jstypeid 
	 *            税收分类编码
     */
    public void setJstypeid(String jstypeid) {
        this.jstypeid = jstypeid == null ? null : jstypeid.trim();
    }

    /**
     * @return 条形码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode 
	 *            条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * @return 添加人
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加人
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 添加人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
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
     * @return 修改人
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改人名称
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