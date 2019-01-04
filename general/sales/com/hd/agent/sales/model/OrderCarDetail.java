package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.GoodsInfo;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 车售订单商品明细
 * @author zhengziyong
 *
 */
public class OrderCarDetail implements Serializable {
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
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;
    /**
     * 可用量
     */
    private BigDecimal usablenum;
    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 主单位余数量
     */
    private BigDecimal overnum;

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
     * 合计箱数
     */
    private BigDecimal totalbox;
    /**
     * 含税单价
     */
    private BigDecimal taxprice;

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
     * 初始单价
     */
    private BigDecimal initprice;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 系统价
     */
    private BigDecimal sysprice;
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
     * 交货日期
     */
    private String deliverydate;

    /**
     * 批次号
     */
    private String batchno;

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
     * 最新采购价
     */
    private BigDecimal buyprice ;

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
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
        return sendnummain;
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

    public BigDecimal getInitprice() {
        return initprice;
    }

    public void setInitprice(BigDecimal initprice) {
        this.initprice = initprice;
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

    public BigDecimal getUsablenum() {
        return usablenum;
    }

    public void setUsablenum(BigDecimal usablenum) {
        this.usablenum = usablenum;
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

    public BigDecimal getSysprice() {
        return sysprice;
    }

    public void setSysprice(BigDecimal sysprice) {
        this.sysprice = sysprice;
    }

    public String getSupplieruser() {
        return supplieruser;
    }

    public void setSupplieruser(String supplieruser) {
        this.supplieruser = supplieruser;
    }

    public BigDecimal getTotalbox() {
        return totalbox;
    }

    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }


}