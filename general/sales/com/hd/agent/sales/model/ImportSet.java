package com.hd.agent.sales.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class ImportSet implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 导入模版名称
     */
    private String name;

    /**
     * 导入模版对应url
     */
    private String url;

    /**
     * 客户分配类型1指定客户编号2指定总店按店号分配3按客户助记码分配4按客户名称分配5按客户简称分配6按客户地址分配
     */
    private String ctype;
    /**
     * 商品类型类型1按条形码导入2按店内码导入3助记符4商品编码
     */
    private String gtype;

    /**
     * 根据ctype值对应
     */
    private String busid;

    private String busname;

    /**
     * 对应模版路径（相对路径）
     */
    private String filepath;

    /**
     * 状态1启用0禁用
     */
    private String state;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
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
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 审核人编号
     */
    private String audituserid;

    /**
     * 审核人名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 中止人编号
     */
    private String stopuserid;

    /**
     * 中止人名称
     */
    private String stopusername;

    /**
     * 中止时间
     */
    private Date stoptime;

    /**
     * 关闭时间
     */
    private Date closetime;
    /**
     * 导入文件参数
     */
    private String fileparam;
    /**
     * 取价方式 0取系统价格1取导入时模板价格
     */
    private String pricetype;

    /**
     * 供应商编码
     */
    private  String supplierid;
    /**
     * 模板类型 1.销售订单模板 2.代配送模板
     */
    private String modeltype;
    /**
     * 备注
     */
    private String remark;
    /**
     * 首行参数
     */
    private String firstcol;
    /**
     * 商品行参数
     */
    private String goodscol;
    /**
     * 开始列参数
     */
    private String beginrow;
    /**
     * 商品标识参数
     */
    private String productcol;
    /**
     * 数量标识0商品数量1商品箱数
     */
    private String numid;
    /**
     * 数量标识参数
     */
    private String numcol;
    /**
     * 客户单号位置
     */
    private String customercol;
    /**
     * 商品单价(零售价)参数
     */
    private String pricecol;
    /**
     * 商品成本价参数
     */
    private String costpricecol;
    /**
     * 开始单元格参数
     */
    private String begincell;
    /**
     * 有效列参数
     */
    private String validcol;
    /**
     * html模板首行
     */
    private String htmlFirstRow;
    /**
     * 数据位置参数
     */
    private String dataposition;
    /**
     * 客户单元格 读取导入的客户信息 如编码，名称，助记符，简称，地址等
     */
    private String customercell;
    /**
     * 供应商参数
     */
    private String suppliercol;
    /**
     * 仓库参数
     */
    private String storagecol;
    /**
     * 备注所在单元格
     */
    private String remarkcell;
    /**
     * 拆分所在列
     */
    private String dividecell;
    /**
     * 业务日期/其它列
     */
    private String othercol;
    /**
     * 客户信息正则
     */
    private String regularcustomer;
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
     * @return 导入模版名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            导入模版名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 导入模版对应url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            导入模版对应url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return 客户分配类型1指定客户编号2指定总店按店号分配3按客户助记码分配4按客户名称分配5按客户简称分配6按客户地址分配
     */
    public String getCtype() {
        return ctype;
    }

    /**
     * @param ctype 
	 *            客户分配类型1指定客户编号2指定总店按店号分配3按客户助记码分配4按客户名称分配5按客户简称分配6按客户地址分配
     */
    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
    }

    /**
     * @return 根据ctype值对应
     */
    public String getBusid() {
        return busid;
    }

    /**
     * @param busid 
	 *            根据ctype值对应
     */
    public void setBusid(String busid) {
        this.busid = busid == null ? null : busid.trim();
    }

    /**
     * @return 对应模版路径（相对路径）
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * @param filepath 
	 *            对应模版路径（相对路径）
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    /**
     * @return 状态1启用0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1启用0禁用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 制单人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人姓名
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
     * @return 最后修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
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
     * @return 审核人编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核人名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @param auditusername 
	 *            审核人名称
     */
    public void setAuditusername(String auditusername) {

        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * @return 审核时间
     */
    public Date getAudittime() {
        return audittime;
    }

    /**
     * @param audittime 
	 *            审核时间
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * @return 中止人编号
     */
    public String getStopuserid() {
        return stopuserid;
    }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    /**
     * @param stopuserid 
	 *            中止人编号
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid == null ? null : stopuserid.trim();
    }

    /**
     * @return 中止人名称
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * @param stopusername 
	 *            中止人名称
     */
    public void setStopusername(String stopusername) {
        this.stopusername = stopusername == null ? null : stopusername.trim();
    }

    /**
     * @return 中止时间
     */
    public Date getStoptime() {
        return stoptime;
    }

    /**
     * @param stoptime 
	 *            中止时间
     */
    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * @return 关闭时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            关闭时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public String getFileparam() {
        return fileparam;
    }

    public void setFileparam(String fileparam) {
        this.fileparam = fileparam;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public String getModeltype() {
        return modeltype;
    }

    public void setModeltype(String modeltype) {
        this.modeltype = modeltype;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getFirstcol() {
        return firstcol;
    }

    public void setFirstcol(String firstcol) {
        this.firstcol = firstcol;
    }

    public String getGoodscol() {
        return goodscol;
    }

    public void setGoodscol(String goodscol) {
        this.goodscol = goodscol;
    }

    public String getBeginrow() {
        return beginrow;
    }

    public void setBeginrow(String beginrow) {
        this.beginrow = beginrow;
    }

    public String getProductcol() {
        return productcol;
    }

    public void setProductcol(String productcol) {
        this.productcol = productcol;
    }

    public String getNumid() {
        return numid;
    }

    public void setNumid(String numid) {
        this.numid = numid;
    }

    public String getNumcol() {
        return numcol;
    }

    public void setNumcol(String numcol) {
        this.numcol = numcol;
    }

    public String getCustomercol() {
        return customercol;
    }

    public void setCustomercol(String customercol) {
        this.customercol = customercol;
    }

    public String getPricecol() {
        return pricecol;
    }

    public void setPricecol(String pricecol) {
        this.pricecol = pricecol;
    }

    public String getCostpricecol() {
        return costpricecol;
    }

    public void setCostpricecol(String costpricecol) {
        this.costpricecol = costpricecol;
    }

    public String getBegincell() {
        return begincell;
    }

    public void setBegincell(String begincell) {
        this.begincell = begincell;
    }

    public String getValidcol() {
        return validcol;
    }

    public void setValidcol(String validcol) {
        this.validcol = validcol;
    }

    public String getHtmlFirstRow() {
        return htmlFirstRow;
    }

    public void setHtmlFirstRow(String htmlFirstRow) {
        this.htmlFirstRow = htmlFirstRow;
    }

    public String getDataposition() {
        return dataposition;
    }

    public void setDataposition(String dataposition) {
        this.dataposition = dataposition;
    }

    public String getCustomercell() {
        return customercell;
    }

    public void setCustomercell(String customercell) {
        this.customercell = customercell;
    }

    public String getSuppliercol() {
        return suppliercol;
    }

    public void setSuppliercol(String suppliercol) {
        this.suppliercol = suppliercol;
    }

    public String getStoragecol() {
        return storagecol;
    }

    public void setStoragecol(String storagecol) {
        this.storagecol = storagecol;
    }

    public String getRemarkcell() {
        return remarkcell;
    }

    public void setRemarkcell(String remarkcell) {
        this.remarkcell = remarkcell;
    }

    public String getDividecell() {
        return dividecell;
    }

    public void setDividecell(String dividecell) {
        this.dividecell = dividecell;
    }

    public String getOthercol() {
        return othercol;
    }

    public void setOthercol(String othercol) {
        this.othercol = othercol;
    }

    public String getRegularcustomer() {
        return regularcustomer;
    }

    public void setRegularcustomer(String regularcustomer) {
        this.regularcustomer = regularcustomer;
    }
}