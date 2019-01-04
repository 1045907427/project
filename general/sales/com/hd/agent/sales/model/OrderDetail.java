package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.GoodsInfo;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 销售订单商品明细
 * @author zhengziyong
 *
 */
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 单据编号
     */
    private String orderid;

    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 店内码
     */
    private String shopid;
    /**
     * 商品组编号
     */
    private String groupid;
    /**
     * 商品分类
     */
    private String goodssort;
    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌业务员
     */
    private String branduser;
    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 厂家业务员
     */
    private String supplieruser;
    /**
     * 是否折扣1是0否
     */
    private String isdiscount;
    /**
     * 是否品牌折扣1是0否
     */
    private String isbranddiscount;

    /**
     * 摊分类型
     */
    private String repartitiontype;

    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    private BigDecimal fixnum;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 主单位余数量
     */
    private BigDecimal overnum;
    /**
     * 合计箱数
     */
    private BigDecimal totalbox;
    /**
     * 辅计量单位
     */
    private String auxunitid;

    /**
     * 辅计量单位名称
     */
    private String auxunitname;

    /**
     * 数量(辅计量)
     */
    private BigDecimal auxnum;

    /**
     * 数量(辅计量显示)
     */
    private String auxnumdetail;
    /**
     * 参考价
     */
    private BigDecimal referenceprice;
    /**
     * 原价
     */
    private BigDecimal fixprice;

    /**
     * 促销价
     */
    private BigDecimal offprice;

    /**
     * 含税单价
     */
    private BigDecimal taxprice;
    /**
     * 含税箱价
     */
    private BigDecimal boxprice;
    /**
     * 含税金额
     */
    private BigDecimal taxamount;

    /**
     * 无税单价
     */
    private BigDecimal notaxprice;

    /**
     * 无税金额
     */
    private BigDecimal notaxamount;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 最低销售价
     */
    private BigDecimal lowestsaleprice;
    /**
     * 基准价
     */
    private BigDecimal basesaleprice;

    /**
     * 税种
     */
    private String taxtype;

    /**
     * 税种名称
     */
    private String taxtypename;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 备注
     */
    private String remark;
    /**
     * 交货类型：0正常1赠品2样品
     */
    private String deliverytype;
    /**
     * 交货日期
     */
    private String deliverydate;

    /**
     * 批次号
     */
    private String batchno;
    /**
     * 批次现存量编号
     */
    private String summarybatchid;
    /**
     * 库位
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;
    /**
     * 生产日期
     */
    private String produceddate;
    /**
     * 有效截止日期
     */
    private String deadline;
    /**
     * 过期日期
     */
    private Date expirationdate;

    /**
     * 来源单据编号
     */
    private String billno;

    /**
     * 来源单位明细编号
     */
    private String billdetailno;

    /**
     * 自定义信息1
     */
    private String field01;

    /**
     * 自定义信息2
     */
    private String field02;

    /**
     * 自定义信息3
     */
    private String field03;

    /**
     * 自定义信息4
     */
    private String field04;

    /**
     * 自定义信息5
     */
    private String field05;

    /**
     * 自定义信息6
     */
    private String field06;

    /**
     * 自定义信息7
     */
    private String field07;

    /**
     * 自定义信息8
     */
    private String field08;

    /**
     * 已通知发货主单位数量
     */
    private BigDecimal sendnummain;

    /**
     * 已通知发货辅单位数量
     */
    private BigDecimal sendnumaux;

    /**
     * 未通知发货主单位数量
     */
    private BigDecimal nosendnummain;

    /**
     * 未通知发货辅单位数量
     */
    private BigDecimal nosendnumaux;

    /**
     * 已通知发货无税金额
     */
    private BigDecimal sendamountnotax;

    /**
     * 已通知发货含税金额
     */
    private BigDecimal sendamounttax;

    /**
     * 未通知发货无税金额
     */
    private BigDecimal nosendamountnotax;

    /**
     * 已通知发货含税金额
     */
    private BigDecimal nosendamounttax;

    /**
     * 对应的商品信息
     */
    private GoodsInfo goodsInfo;
    /**
     * 排序
     */
    private int seq;

    /**
     * 固定价
     */
    private BigDecimal oldprice;

    private String storageid;

    private String storagename;
    /**
     * 判断仓库商品数量是否足够 1足够 0不足
     */
    private String Isenough;
    /**
     * 可用量
     */
    private BigDecimal usablenum;

    /**
     * 要货单价
     */
    private BigDecimal demandprice;

    /**
     * 要货金额
     */
    private BigDecimal demandamount;
    /**
     * 是否已补货1是0否
     */
    private String issupply;
    /**
     * 是否显示1是0否（配置库存时，保留没有商品的明细）
     */
    private String isview;

    /**
     * 最新采购价
     */
    private BigDecimal buyprice ;
    /**
     * 商品总箱重
     */
    private BigDecimal totalboxweight;
    /**
     * 商品总箱体积
     */
    private BigDecimal totalboxvolume;

    /**
     * 是否新品
     */
    private String isnew;

    /**
     * 关联的订货单关系表的id
     * @return
     */
    private String relationordergoodsid;

    /**
     * 订货单数量
     * @return
     */
    private BigDecimal goodsMaxNum;

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public String getIsview() {
        return isview;
    }

    public void setIsview(String isview) {
        this.isview = isview;
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
        this.id = id;
    }

    /**
     * @return 单据编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid
     *            单据编号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * @return 商品编号
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid
     *            商品编号
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    /**
     * @return 主计量单位
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid
     *            主计量单位
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    /**
     * @return 主计量单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname
     *            主计量单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    /**
     * @return 数量
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum
     *            数量
     */
    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public BigDecimal getFixnum() {
        return fixnum;
    }

    public void setFixnum(BigDecimal fixnum) {
        this.fixnum = fixnum;
    }

    public BigDecimal getOvernum() {
        return overnum;
    }

    public void setOvernum(BigDecimal overnum) {
        this.overnum = overnum;
    }

    /**
     * @return 辅计量单位
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid
     *            辅计量单位
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid;
    }

    /**
     * @return 辅计量单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname
     *            辅计量单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname;
    }

    /**
     * @return 数量(辅计量)
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum
     *            数量(辅计量)
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 数量(辅计量显示)
     */
    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    /**
     * @param auxnumdetail
     *            数量(辅计量显示)
     */
    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail;
    }

    /**
     *
     * @return 原价
     * @author zhengziyong
     * @date Nov 27, 2013
     */
    public BigDecimal getFixprice() {
        return fixprice;
    }

    /**
     *
     * @param fixprice 原价
     * @author zhengziyong
     * @date Nov 27, 2013
     */
    public void setFixprice(BigDecimal fixprice) {
        this.fixprice = fixprice;
    }

    /**
     *
     * @return 促销价
     * @author zhengziyong
     * @date Nov 27, 2013
     */
    public BigDecimal getOffprice() {
        return offprice;
    }

    /**
     *
     * @param offprice 促销价
     * @author zhengziyong
     * @date Nov 27, 2013
     */
    public void setOffprice(BigDecimal offprice) {
        this.offprice = offprice;
    }

    /**
     * @return 含税单价
     */
    public BigDecimal getTaxprice() {
        return taxprice;
    }

    /**
     * @param taxprice
     *            含税单价
     */
    public void setTaxprice(BigDecimal taxprice) {
        this.taxprice = taxprice;
    }

    /**
     * @return 含税金额
     */
    public BigDecimal getTaxamount() {
        return taxamount;
    }

    /**
     * @param taxamount
     *            含税金额
     */
    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    /**
     * @return 无税单价
     */
    public BigDecimal getNotaxprice() {
        return notaxprice;
    }

    /**
     * @param notaxprice
     *            无税单价
     */
    public void setNotaxprice(BigDecimal notaxprice) {
        this.notaxprice = notaxprice;
    }

    /**
     * @return 无税金额
     */
    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    /**
     * @param notaxamount
     *            无税金额
     */
    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    /**
     * @return 税种
     */
    public String getTaxtype() {
        return taxtype;
    }

    /**
     * @param taxtype
     *            税种
     */
    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    /**
     *
     * @return 税种名称
     * @author zhengziyong
     * @date May 14, 2013
     */
    public String getTaxtypename() {
        return taxtypename;
    }

    /**
     *
     * @param taxtypename 税种名称
     * @author zhengziyong
     * @date May 14, 2013
     */
    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
    }

    /**
     * @return 税额
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * @param tax
     *            税额
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
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
        this.remark = remark;
    }

    /**
     * @return 交货日期
     */
    @JSON(format="yyyy-MM-dd")
    public String getDeliverydate() {
        return deliverydate;
    }

    /**
     * @param deliverydate
     *            交货日期
     */
    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    /**
     * @return 批次号
     */
    public String getBatchno() {
        return batchno;
    }

    /**
     * @param batchno
     *            批次号
     */
    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    /**
     * @return 过期日期
     */
    @JSON(format="yyyy-MM-dd")
    public Date getExpirationdate() {
        return expirationdate;
    }

    /**
     * @param expirationdate
     *            过期日期
     */
    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
    }

    /**
     * @return 来源单据编号
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno
     *            来源单据编号
     */
    public void setBillno(String billno) {
        this.billno = billno;
    }

    /**
     * @return 来源单位明细编号
     */
    public String getBilldetailno() {
        return billdetailno;
    }

    /**
     * @param billdetailno
     *            来源单位明细编号
     */
    public void setBilldetailno(String billdetailno) {
        this.billdetailno = billdetailno;
    }

    /**
     * @return 自定义信息1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01
     *            自定义信息1
     */
    public void setField01(String field01) {
        this.field01 = field01;
    }

    /**
     * @return 自定义信息2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02
     *            自定义信息2
     */
    public void setField02(String field02) {
        this.field02 = field02;
    }

    /**
     * @return 自定义信息3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03
     *            自定义信息3
     */
    public void setField03(String field03) {
        this.field03 = field03;
    }

    /**
     * @return 自定义信息4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04
     *            自定义信息4
     */
    public void setField04(String field04) {
        this.field04 = field04;
    }

    /**
     * @return 自定义信息5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05
     *            自定义信息5
     */
    public void setField05(String field05) {
        this.field05 = field05;
    }

    /**
     * @return 自定义信息6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06
     *            自定义信息6
     */
    public void setField06(String field06) {
        this.field06 = field06;
    }

    /**
     * @return 自定义信息7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07
     *            自定义信息7
     */
    public void setField07(String field07) {
        this.field07 = field07;
    }

    /**
     * @return 自定义信息8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08
     *            自定义信息8
     */
    public void setField08(String field08) {
        this.field08 = field08;
    }

    /**
     * @return 已通知发货主单位数量
     */
    public BigDecimal getSendnummain() {
        return sendnummain;
    }

    /**
     * @param sendnummain
     *            已通知发货主单位数量
     */
    public void setSendnummain(BigDecimal sendnummain) {
        this.sendnummain = sendnummain;
    }

    /**
     * @return 已通知发货辅单位数量
     */
    public BigDecimal getSendnumaux() {
        return sendnumaux;
    }

    /**
     * @param sendnumaux
     *            已通知发货辅单位数量
     */
    public void setSendnumaux(BigDecimal sendnumaux) {
        this.sendnumaux = sendnumaux;
    }

    /**
     * @return 未通知发货主单位数量
     */
    public BigDecimal getNosendnummain() {
        return nosendnummain;
    }

    /**
     * @param nosendnummain
     *            未通知发货主单位数量
     */
    public void setNosendnummain(BigDecimal nosendnummain) {
        this.nosendnummain = nosendnummain;
    }

    /**
     * @return 未通知发货辅单位数量
     */
    public BigDecimal getNosendnumaux() {
        return nosendnumaux;
    }

    /**
     * @param nosendnumaux
     *            未通知发货辅单位数量
     */
    public void setNosendnumaux(BigDecimal nosendnumaux) {
        this.nosendnumaux = nosendnumaux;
    }

    /**
     * @return 已通知发货无税金额
     */
    public BigDecimal getSendamountnotax() {
        return sendamountnotax;
    }

    /**
     * @param sendamountnotax
     *            已通知发货无税金额
     */
    public void setSendamountnotax(BigDecimal sendamountnotax) {
        this.sendamountnotax = sendamountnotax;
    }

    /**
     * @return 已通知发货含税金额
     */
    public BigDecimal getSendamounttax() {
        return sendamounttax;
    }

    /**
     * @param sendamounttax
     *            已通知发货含税金额
     */
    public void setSendamounttax(BigDecimal sendamounttax) {
        this.sendamounttax = sendamounttax;
    }

    /**
     * @return 未通知发货无税金额
     */
    public BigDecimal getNosendamountnotax() {
        return nosendamountnotax;
    }

    /**
     * @param nosendamountnotax
     *            未通知发货无税金额
     */
    public void setNosendamountnotax(BigDecimal nosendamountnotax) {
        this.nosendamountnotax = nosendamountnotax;
    }

    /**
     * @return 已通知发货含税金额
     */
    public BigDecimal getNosendamounttax() {
        return nosendamounttax;
    }

    /**
     * @param nosendamounttax
     *            已通知发货含税金额
     */
    public void setNosendamounttax(BigDecimal nosendamounttax) {
        this.nosendamounttax = nosendamounttax;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public BigDecimal getCostprice() {
        return costprice;
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getBranduser() {
        return branduser;
    }

    public void setBranduser(String branduser) {
        this.branduser = branduser;
    }

    public String getBranddept() {
        return branddept;
    }

    public void setBranddept(String branddept) {
        this.branddept = branddept;
    }

    /**
     * @return 固定价
     * @author zhengziyong
     * @date Dec 14, 2013
     */
    public BigDecimal getOldprice() {
        return oldprice;
    }

    /**
     * @param oldprice 固定价
     * @author zhengziyong
     * @date Dec 14, 2013
     */
    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getIsenough() {
        return Isenough;
    }

    public void setIsenough(String isenough) {
        Isenough = isenough;
    }

    public BigDecimal getUsablenum() {
        return usablenum;
    }

    public void setUsablenum(BigDecimal usablenum) {
        this.usablenum = usablenum;
    }

    /**
     *
     * @return 要货单价
     * @author zhengziyong
     * @date Jan 4, 2014
     */
    public BigDecimal getDemandprice() {
        return demandprice;
    }

    /**
     *
     * @param demandprice 要货单价
     * @author zhengziyong
     * @date Jan 4, 2014
     */
    public void setDemandprice(BigDecimal demandprice) {
        this.demandprice = demandprice;
    }

    /**
     *
     * @return 要货金额
     * @author zhengziyong
     * @date Jan 4, 2014
     */
    public BigDecimal getDemandamount() {
        return demandamount;
    }

    /**
     *
     * @param demandamount 要货金额
     * @author zhengziyong
     * @date Jan 4, 2014
     */
    public void setDemandamount(BigDecimal demandamount) {
        this.demandamount = demandamount;
    }
    /**
     * 是否折扣1是0否
     * @return
     * @author chenwei
     * @date 2014年6月21日
     */
    public String getIsdiscount() {
        return isdiscount;
    }
    /**
     * 是否折扣1是0否
     * @param isdiscount
     * @author chenwei
     * @date 2014年6月21日
     */
    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount;
    }
    /**
     * 是否品牌折扣1是0否
     * @return
     * @author chenwei
     * @date 2014年6月21日
     */
    public String getIsbranddiscount() {
        return isbranddiscount;
    }
    /**
     * 是否品牌折扣1是0否
     * @param isbranddiscount
     * @author chenwei
     * @date 2014年6月21日
     */
    public void setIsbranddiscount(String isbranddiscount) {
        this.isbranddiscount = isbranddiscount;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getGoodssort() {
        return goodssort;
    }

    public void setGoodssort(String goodssort) {
        this.goodssort = goodssort;
    }

    public String getSupplieruser() {
        return supplieruser;
    }

    public void setSupplieruser(String supplieruser) {
        this.supplieruser = supplieruser;
    }

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

    public String getIssupply() {
        return issupply;
    }

    public void setIssupply(String issupply) {
        this.issupply = issupply;
    }

    public BigDecimal getTotalbox() {
        return totalbox;
    }

    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    public BigDecimal getLowestsaleprice() {
        return lowestsaleprice;
    }

    public void setLowestsaleprice(BigDecimal lowestsaleprice) {
        this.lowestsaleprice = lowestsaleprice;
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getStoragelocationid() {
        return storagelocationid;
    }

    public String getProduceddate() {
        return produceddate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid;
    }

    public void setProduceddate(String produceddate) {
        this.produceddate = produceddate;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSummarybatchid() {
        return summarybatchid;
    }

    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getStoragelocationname() {
        return storagelocationname;
    }

    public void setStoragelocationname(String storagelocationname) {
        this.storagelocationname = storagelocationname;
    }

    public BigDecimal getTotalboxweight() {
        return totalboxweight;
    }

    public void setTotalboxweight(BigDecimal totalboxweight) {
        this.totalboxweight = totalboxweight;
    }

    public BigDecimal getTotalboxvolume() {
        return totalboxvolume;
    }

    public void setTotalboxvolume(BigDecimal totalboxvolume) {
        this.totalboxvolume = totalboxvolume;
    }

    public BigDecimal getBasesaleprice() {
        return basesaleprice;
    }

    public void setBasesaleprice(BigDecimal basesaleprice) {
        this.basesaleprice = basesaleprice;
    }

    public String getRepartitiontype() {
        return repartitiontype;
    }

    public void setRepartitiontype(String repartitiontype) {
        this.repartitiontype = repartitiontype;
    }

    public BigDecimal getReferenceprice() {
        return referenceprice;
    }

    public void setReferenceprice(BigDecimal referenceprice) {
        this.referenceprice = referenceprice;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getRelationordergoodsid() {
        return relationordergoodsid;
    }

    public void setRelationordergoodsid(String relationordergoodsid) {
        this.relationordergoodsid = relationordergoodsid;
    }

    public BigDecimal getGoodsMaxNum() {
        return goodsMaxNum;
    }

    public void setGoodsMaxNum(BigDecimal goodsMaxNum) {
        this.goodsMaxNum = goodsMaxNum;
    }
}