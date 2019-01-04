package com.hd.agent.basefiles.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String edittype;
    /**
     * 要删除主计量单位IDs
     */
    private String delMUIds;

    /**
     * 要删除的价格套ids
     */
    private String delPriceIds;
    
    /**
     * 要删除对应仓库ids
     */
    private String delStorageIds;

    /**
     * 要删除对应分类ids
     */
    private String delWCIds;
    
    /**
     * 要删除的对应库位
     */
    private String delSLIds;
    
    /**
     * 旧编码
     */
    private String oldId;
    /**
     * 商品促销类型0正常商品1买赠2捆绑
     */
    private String ptype;
    /**
     * 编码
     */
    private String id;
    /**
     * 店内码
     */
    private String shopid;
    /**
     * 名称
     */
    private String name;
    
    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 状态4新增3暂存2保存1启用0禁用
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String stateName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 主计量单位（来源计量单位档案）
     */
    private String mainunit;
    
    /**
     * 主计量单位名称
     */
    private String mainunitName;
    
    /**
     * 箱装量
     */
    private BigDecimal boxnum;

    /**
     * 税率
     */
    private BigDecimal taxrate;
    
    /**
     * 辅单位编号
     */
    private String auxunitid;
    
    /**
     * 辅单位名称
     */
    private String auxunitname;
    
    /**
     * 价格套
     */
    private List<GoodsInfo_PriceInfo> priceList;
    /**
     * 商品类型1普通2虚拟3费用4其他
     */
    private String goodstype;
    
    /**
     * 商品类型名称
     */
    private String goodstypeName;

    /**
     * 助记符
     */
    private String spell;

    /**
     * 商品品牌（来源商品品牌档案）
     */
    private String brand;
    
    /**
     * 商品品牌名称
     */
    private String brandName;
    
    /**
     * 所属部门
     */
    private String deptid;
    
    /**
     * 所属部门名称
     */
    private String deptname;
    
    /**
     * 原来的商品品牌编码
     */
    private String oldbrand;
    
    /**
     * 品牌部门
     */
    private String branddept;

    /**
     * ABC码
     */
    private String abclevel;

    /**
     * 默认分类（来源商品分类）
     */
    private String defaultsort;

    /**
     * 商品分类名称
     */
    private String defaultsortName;
    
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 箱装条形码
     */
    private String boxbarcode;

    /**
     * 货号
     */
    private String itemno;

    /**
     * 购销类型1购销2可购3可销4其他
     */
    private String bstype;

    /**
     * 购销类型名称
     */
    private String bstypeName;

    /**
     * 产地
     */
    private String productfield;
    /**
     * 排序码
     */
    private String sortkey;

    /**
     * 是否允许出入库1是0否
     */
    private String isinoutstorage;

    /**
     * 默认仓库
     */
    private String storageid;
    
    /**
     * 默认仓库名称
     */
    private String storageName;

    /**
     * 默认库位
     */
    private String storagelocation;
    
    /**
     * 默认库位名称
     */
    private String storagelocationName;
    
    /**
     * 对应库位库位容量
     */
    private BigDecimal slboxnum;
    
    /**
     * 是否批次管理1是0否
     */
    private String isbatch;
    
    /**
     * 是否批次管理名称
     */
    private String isbatchname;

    /**
     * 是否库位管理1是0否
     */
    private String isstoragelocation;
    
    /**
     * 是否库位管理名称
     */
    private String isstoragelocationname;

    /**
     * 是否保质期管理1是0否
     */
    private String isshelflife;
    
    /**
     * 是否保质期管理名称
     */
    private String isshelflifename;

    /**
     * 保质期
     */
    private BigDecimal shelflife;

    /**
     * 保质期单位1天2周3月4年
     */
    private String shelflifeunit;
    
    /**
     * 保质期单位名称
     */
    private String shelflifeunitName;
    
    /**
	  * 保质期描述 = 保质期 + 保质期单位
	  */
	 private String shelflifedetail;

    /**
     * 最高采购价
     */
    private BigDecimal highestbuyprice;
    
    /**
     * 采购箱价
     */
    private BigDecimal buyboxprice;

    /**
     * 最低销售价
     */
    private BigDecimal lowestsaleprice;

    /**
     * 基准销售价
     */
    private BigDecimal basesaleprice;

    /**
     * 最高库存
     */
    private BigDecimal highestinventory;

    /**
     * 最低库存
     */
    private BigDecimal lowestinventory;

    /**
     * 安全库存
     */
    private BigDecimal safeinventory;

    /**
     * 标准价
     */
    private BigDecimal normalprice;

    /**
     * 盘点方式1定期盘点
     */
    private String checktype;
    
    /**
     * 盘点方式名称
     */
    private String checktypeName;
    
    /**
     * 盘点周期
     */
    private Long checkdate;

    /**
     * 盘点周期单位1天2周3月4年
     */
    private String checkunit;

    /**
     * 盘点周期单位名称
     */
    private String checkunitName;
    
    /**
     * 默认采购员（来源人员档案）
     */
    private String defaultbuyer;
    
    /**
     * 默认采购员名称
     */
    private String defaultbuyerName;
    
    /**
     * 默认业务员（来源人员档案）
     */
    private String defaultsaler;

    /**
     * 默认业务员名称
     */
    private String defaultsalerName;
    
    /**
     * 默认供应商（来源供应商档案）
     */
    private String defaultsupplier;
    
    /**
     * 默认供应商名称
     */
    private String defaultsupplierName;
    
    /**
     * 第二供应商
     */
    private String secondsupplier;
    
    /**
     * 第二供应商名称
     */
    private String secondsuppliername;
    
    /**
     * 原来供应商编码
     */
    private String olddefaultsupplier;

    /**
     * 默认税种（来源税种档案）
     */
    private String defaulttaxtype;
    
    /**
     * 默认税种名称
     */
    private String defaulttaxtypeName;
    
    /**
     * 计划毛利率
     */
    private BigDecimal planmargin;

    /**
     * 最新采购价
     */
    private BigDecimal newbuyprice;

    /**
     * 最新销售价
     */
    private BigDecimal newsaleprice;

    /**
     * 最新库存价
     */
    private BigDecimal newstorageprice;

    /**
     * 最新采购日期
     */
    private String newbuydate;

    /**
     * 最新销售日期
     */
    private String newsaledate;

    /**
     * 每单平均销量
     */
    private BigDecimal everybillaveragesales;

    /**
     * 最新入库日期
     */
    private String newinstroragedate;

    /**
     * 最新出库日期
     */
    private String newoutstoragedate;

    /**
     * 最新库存
     */
    private BigDecimal newinventory;

    /**
     * 最新盘点日期
     */
    private String newcheckdate;

    /**
     * 商品形状1方型2球型3圆柱4其他
     */
    private String gshape;
    
    /**
     * 商品形状名称
     */
    private String gshapeName;
    
    /**
     * 商品长度
     */
    private BigDecimal glength;

    /**
     * 长度转换因子（m）
     */
    private BigDecimal gmlength;

    /**
     * 宽度
     */
    private BigDecimal gwidth;

    /**
     * 宽度转换因子
     */
    private BigDecimal gmwidth;

    /**
     * 高度
     */
    private BigDecimal ghight;

    /**
     * 高度转换因子
     */
    private BigDecimal gmhight;

    /**
     * 直径
     */
    private BigDecimal gdiameter;

    /**
     * 直径转换因子
     */
    private BigDecimal gmdiameter;

    /**
     * 毛重
     */
    private BigDecimal grossweight;

    /**
     * 净重
     */
    private BigDecimal netweight;
    
    /**
     * 箱重
     */
    private BigDecimal totalweight;
    
    /**
     * 箱体积
     */
    private BigDecimal totalvolume;
    
    /**
     * 单体积
     */
    private BigDecimal singlevolume;

    /**
     * 重量转换因子kg
     */
    private BigDecimal kgweight;
    
    /**
     * 照片
     */
    private String image;
    
    /**
     * 照片编号集
     */
    private String imageids;

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
     * 自定义信息9
     */
    private String field09;

    /**
     * 建档人用户编号
     */
    private String adduserid;

    /**
     * 建档人姓名
     */
    private String addusername;

    /**
     * 建档人部门编号
     */
    private String adddeptid;

    /**
     * 建档部门名称
     */
    private String adddeptname;

    /**
     * 建档时间
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
     * 物理备注
     */
    private String physicsremark;

    /**
     * 自定义信息10
     */
    private String field10;

    /**
     * 自定义信息11
     */
    private String field11;

    /**
     * 自定义信息12
     */
    private String field12;
    /**
     * 最新采购总数量
     */
    private BigDecimal newtotalbuynum;
    /**
     * 最新采购总金额
     */
    private BigDecimal newtotalbuyamount;
    /**
     * 核算成本价
     */
    private BigDecimal costaccountprice;
    
    /**
     * 最小发货单位
     */
    private Integer minimum;
    
    /**
	  * 1号价
	  */
	 private BigDecimal price1;
	 
	 /**
	  * 2号价
	  */
	 private BigDecimal price2;
	 
	 /**
	  * 3号价
	  */
	 private BigDecimal price3;
	 
	 /**
	  * 4号价
	  */
	 private BigDecimal price4;
	 
	 /**
	  * 5号价
	  */
	 private BigDecimal price5;
	 
	 /**
	  * 6号价
	  */
	 private BigDecimal price6;
	 
	 /**
	  * 7号价
	  */
	 private BigDecimal price7;
	 
	 /**
	  * 8号价
	  */
	 private BigDecimal price8;
	 
	 /**
	  * 9号价
	  */
	 private BigDecimal price9;
	 
	 /**
	  * 10号价
	  */
	 private BigDecimal price10;

    /**
     * 1号价箱价
     */
    private BigDecimal boxprice1;

    /**
     * 2号价箱价
     */
    private BigDecimal boxprice2;

    /**
     * 3号价箱价
     */
    private BigDecimal boxprice3;

    /**
     * 4号价箱价
     */
    private BigDecimal boxprice4;

    /**
     * 5号价箱价
     */
    private BigDecimal boxprice5;

    /**
     * 6号价箱价
     */
    private BigDecimal boxprice6;

    /**
     * 7号价箱价
     */
    private BigDecimal boxprice7;

    /**
     * 8号价箱价
     */
    private BigDecimal boxprice8;

    /**
     * 9号价箱价
     */
    private BigDecimal boxprice9;

    /**
     * 10号价箱价
     */
    private BigDecimal boxprice10;
    /**
     * 金税商品编码
     */
    private String jsgoodsid;
    /**
     * 金税商品字段修改人用户编号
     */
    private String jsgoodsmodifyuserid;

    /**
     * 金税商品字段修改人姓名
     */
    private String jsgoodsmodifyusername;

    /**
     * 金税商品字段修改时间
     */
    private Date jsgoodsmodifytime;
    /**
     * 金税科目分类编码
     */
    private String jstaxsortid;
    /**
     * 金税科目分类名称
     */
    private String jstaxsortname;

    /**
     * 是否新品，针对客户
     */
    private String isnew;
    
    public BigDecimal getPrice1() {
		return price1;
	}

	public void setPrice1(BigDecimal price1) {
		this.price1 = price1;
	}

	public BigDecimal getPrice2() {
		return price2;
	}

	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}

	public BigDecimal getPrice3() {
		return price3;
	}

	public void setPrice3(BigDecimal price3) {
		this.price3 = price3;
	}

	public BigDecimal getPrice4() {
		return price4;
	}

	public void setPrice4(BigDecimal price4) {
		this.price4 = price4;
	}

	public BigDecimal getPrice5() {
		return price5;
	}

	public void setPrice5(BigDecimal price5) {
		this.price5 = price5;
	}

	public BigDecimal getPrice6() {
		return price6;
	}

	public void setPrice6(BigDecimal price6) {
		this.price6 = price6;
	}

	public BigDecimal getPrice7() {
		return price7;
	}

	public void setPrice7(BigDecimal price7) {
		this.price7 = price7;
	}

	public BigDecimal getPrice8() {
		return price8;
	}

	public void setPrice8(BigDecimal price8) {
		this.price8 = price8;
	}

	public BigDecimal getPrice9() {
		return price9;
	}

	public void setPrice9(BigDecimal price9) {
		this.price9 = price9;
	}

	public BigDecimal getPrice10() {
		return price10;
	}

	public void setPrice10(BigDecimal price10) {
		this.price10 = price10;
	}

	/**
     * @return 自定义信息10
     */
    public String getField10() {
        return field10;
    }

    /**
     * @param field10 
	 *            自定义信息10
     */
    public void setField10(String field10) {
        this.field10 = field10 == null ? null : field10.trim();
    }

    /**
     * @return 自定义信息11
     */
    public String getField11() {
        return field11;
    }

    /**
     * @param field11 
	 *            自定义信息11
     */
    public void setField11(String field11) {
        this.field11 = field11 == null ? null : field11.trim();
    }

    /**
     * @return 自定义信息12
     */
    public String getField12() {
        return field12;
    }

    /**
     * @param field12 
	 *            自定义信息12
     */
    public void setField12(String field12) {
        this.field12 = field12 == null ? null : field12.trim();
    }
    
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
     * @return 状态4新增3暂存2保存1启用0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态4新增3暂存2保存1启用0禁用
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
     * @return 规格型号
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model 
	 *            规格型号
     */
    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    /**
     * @return 主计量单位（来源计量单位档案）
     */
    public String getMainunit() {
        return mainunit;
    }

    /**
     * @param mainunit 
	 *            主计量单位（来源计量单位档案）
     */
    public void setMainunit(String mainunit) {
        this.mainunit = mainunit == null ? null : mainunit.trim();
    }

    /**
     * @return 商品类型1普通2虚拟3费用4其他
     */
    public String getGoodstype() {
        return goodstype;
    }

    /**
     * @param goodstype 
	 *            商品类型1普通2虚拟3费用4其他
     */
    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype == null ? null : goodstype.trim();
    }

    /**
     * @return 助记符
     */
    public String getSpell() {
        return spell;
    }

    /**
     * @param spell 
	 *            助记符
     */
    public void setSpell(String spell) {
        this.spell = spell == null ? null : spell.trim();
    }

    /**
     * @return 商品品牌（来源商品品牌档案）
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand 
	 *            商品品牌（来源商品品牌档案）
     */
    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    /**
     * @return ABC码
     */
    public String getAbclevel() {
        return abclevel;
    }

    /**
     * @param abclevel 
	 *            ABC码
     */
    public void setAbclevel(String abclevel) {
        this.abclevel = abclevel == null ? null : abclevel.trim();
    }

    /**
     * @return 默认分类（来源商品分类）
     */
    public String getDefaultsort() {
        return defaultsort;
    }

    /**
     * @param defaultsort 
	 *            默认分类（来源商品分类）
     */
    public void setDefaultsort(String defaultsort) {
        this.defaultsort = defaultsort == null ? null : defaultsort.trim();
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
     * @return 箱装条形码
     */
    public String getBoxbarcode() {
        return boxbarcode;
    }

    /**
     * @param boxbarcode 
	 *            箱装条形码
     */
    public void setBoxbarcode(String boxbarcode) {
        this.boxbarcode = boxbarcode == null ? null : boxbarcode.trim();
    }

    /**
     * @return 货号
     */
    public String getItemno() {
        return itemno;
    }

    /**
     * @param itemno 
	 *            货号
     */
    public void setItemno(String itemno) {
        this.itemno = itemno == null ? null : itemno.trim();
    }

    /**
     * @return 购销类型1购销2可购3可销4其他
     */
    public String getBstype() {
        return bstype;
    }

    /**
     * @param bstype 
	 *            购销类型1购销2可购3可销4其他
     */
    public void setBstype(String bstype) {
        this.bstype = bstype == null ? null : bstype.trim();
    }

    /**
     * @return 排序码
     */
    public String getSortkey() {
        return sortkey;
    }

    /**
     * @param sortkey 
	 *            排序码
     */
    public void setSortkey(String sortkey) {
        this.sortkey = sortkey == null ? null : sortkey.trim();
    }

    /**
     * @return 是否允许出入库1是0否
     */
    public String getIsinoutstorage() {
        return isinoutstorage;
    }

    /**
     * @param isinoutstorage 
	 *            是否允许出入库1是0否
     */
    public void setIsinoutstorage(String isinoutstorage) {
        this.isinoutstorage = isinoutstorage == null ? null : isinoutstorage.trim();
    }

    /**
     * @return 默认仓库
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            默认仓库
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 默认库位
     */
    public String getStoragelocation() {
        return storagelocation;
    }

    /**
     * @param storagelocation 
	 *            默认库位
     */
    public void setStoragelocation(String storagelocation) {
        this.storagelocation = storagelocation == null ? null : storagelocation.trim();
    }

    /**
     * @return 是否批次管理1是0否
     */
    public String getIsbatch() {
        return isbatch;
    }

    /**
     * @param isbatch 
	 *            是否批次管理1是0否
     */
    public void setIsbatch(String isbatch) {
        this.isbatch = isbatch == null ? null : isbatch.trim();
    }

    /**
     * @return 是否库位管理1是0否
     */
    public String getIsstoragelocation() {
        return isstoragelocation;
    }

    /**
     * @param isstoragelocation 
	 *            是否库位管理1是0否
     */
    public void setIsstoragelocation(String isstoragelocation) {
        this.isstoragelocation = isstoragelocation == null ? null : isstoragelocation.trim();
    }

    /**
     * @return 是否保质期管理1是0否
     */
    public String getIsshelflife() {
        return isshelflife;
    }

    /**
     * @param isshelflife 
	 *            是否保质期管理1是0否
     */
    public void setIsshelflife(String isshelflife) {
        this.isshelflife = isshelflife == null ? null : isshelflife.trim();
    }

    public BigDecimal getShelflife() {
		return shelflife;
	}

	public void setShelflife(BigDecimal shelflife) {
		this.shelflife = shelflife;
	}

	/**
     * @return 保质期单位1天2周3月4年
     */
    public String getShelflifeunit() {
        return shelflifeunit;
    }

    /**
     * @param shelflifeunit 
	 *            保质期单位1天2周3月4年
     */
    public void setShelflifeunit(String shelflifeunit) {
        this.shelflifeunit = shelflifeunit == null ? null : shelflifeunit.trim();
    }

    /**
     * @return 最高采购价
     */
    public BigDecimal getHighestbuyprice() {
    	if(null!=highestbuyprice && highestbuyprice.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return highestbuyprice;
		}
    }

    /**
     * @param highestbuyprice 
	 *            最高采购价
     */
    public void setHighestbuyprice(BigDecimal highestbuyprice) {
        this.highestbuyprice = highestbuyprice;
    }

    /**
     * @return 最低销售价
     */
    public BigDecimal getLowestsaleprice() {
    	if(null!=lowestsaleprice && lowestsaleprice.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return lowestsaleprice;
		}
    }

    /**
     * @param lowestsaleprice 
	 *            最低销售价
     */
    public void setLowestsaleprice(BigDecimal lowestsaleprice) {
        this.lowestsaleprice = lowestsaleprice;
    }

    /**
     * @return 基准销售价
     */
    public BigDecimal getBasesaleprice() {
    	if(null!=basesaleprice && basesaleprice.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return basesaleprice;
		}
    }

    /**
     * @param basesaleprice 
	 *            基准销售价
     */
    public void setBasesaleprice(BigDecimal basesaleprice) {
        this.basesaleprice = basesaleprice;
    }

    /**
     * @return 最高库存
     */
    public BigDecimal getHighestinventory() {
    	if(null!=highestinventory && highestinventory.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return highestinventory;
		}
    }

    /**
     * @param highestinventory 
	 *            最高库存
     */
    public void setHighestinventory(BigDecimal highestinventory) {
        this.highestinventory = highestinventory;
    }

    /**
     * @return 最低库存
     */
    public BigDecimal getLowestinventory() {
    	if(null!=lowestinventory && lowestinventory.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return lowestinventory;
		}
    }

    /**
     * @param lowestinventory 
	 *            最低库存
     */
    public void setLowestinventory(BigDecimal lowestinventory) {
        this.lowestinventory = lowestinventory;
    }

    /**
     * @return 安全库存
     */
    public BigDecimal getSafeinventory() {
    	if(null!=safeinventory && safeinventory.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return safeinventory;
		}
    }

    /**
     * @param safeinventory 
	 *            安全库存
     */
    public void setSafeinventory(BigDecimal safeinventory) {
        this.safeinventory = safeinventory;
    }

    /**
     * @return 标准价
     */
    public BigDecimal getNormalprice() {
    	if(null!=normalprice && normalprice.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return normalprice;
		}
    }

    /**
     * @param normalprice 
	 *            标准价
     */
    public void setNormalprice(BigDecimal normalprice) {
        this.normalprice = normalprice;
    }

    /**
     * @return 盘点方式1定期盘点
     */
    public String getChecktype() {
        return checktype;
    }

    /**
     * @param checktype 
	 *            盘点方式1定期盘点
     */
    public void setChecktype(String checktype) {
        this.checktype = checktype == null ? null : checktype.trim();
    }

    /**
     * @return 盘点周期
     */
    public Long getCheckdate() {
        return checkdate;
    }

    /**
     * @param checkdate 
	 *            盘点周期
     */
    public void setCheckdate(Long checkdate) {
        this.checkdate = checkdate;
    }

    /**
     * @return 盘点周期单位1天2周3月4年
     */
    public String getCheckunit() {
        return checkunit;
    }

    /**
     * @param checkunit 
	 *            盘点周期单位1天2周3月4年
     */
    public void setCheckunit(String checkunit) {
        this.checkunit = checkunit == null ? null : checkunit.trim();
    }

    /**
     * @return 默认采购员（来源人员档案）
     */
    public String getDefaultbuyer() {
        return defaultbuyer;
    }

    /**
     * @param defaultbuyer 
	 *            默认采购员（来源人员档案）
     */
    public void setDefaultbuyer(String defaultbuyer) {
        this.defaultbuyer = defaultbuyer == null ? null : defaultbuyer.trim();
    }

    /**
     * @return 默认业务员（来源人员档案）
     */
    public String getDefaultsaler() {
        return defaultsaler;
    }

    /**
     * @param defaultsaler 
	 *            默认业务员（来源人员档案）
     */
    public void setDefaultsaler(String defaultsaler) {
        this.defaultsaler = defaultsaler == null ? null : defaultsaler.trim();
    }

    /**
     * @return 默认供应商（来源供应商档案）
     */
    public String getDefaultsupplier() {
        return defaultsupplier;
    }

    /**
     * @param defaultsupplier 
	 *            默认供应商（来源供应商档案）
     */
    public void setDefaultsupplier(String defaultsupplier) {
        this.defaultsupplier = defaultsupplier == null ? null : defaultsupplier.trim();
    }

    /**
     * @return 默认税种（来源税种档案）
     */
    public String getDefaulttaxtype() {
        return defaulttaxtype;
    }

    /**
     * @param defaulttaxtype 
	 *            默认税种（来源税种档案）
     */
    public void setDefaulttaxtype(String defaulttaxtype) {
        this.defaulttaxtype = defaulttaxtype == null ? null : defaulttaxtype.trim();
    }

    /**
     * @return 计划毛利率
     */
    public BigDecimal getPlanmargin() {
    	if(null!=planmargin && planmargin.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return planmargin;
		}
    }

    /**
     * @param planmargin 
	 *            计划毛利率
     */
    public void setPlanmargin(BigDecimal planmargin) {
        this.planmargin = planmargin;
    }

    /**
     * @return 最新采购价
     */
    public BigDecimal getNewbuyprice() {
    	if(null!=newbuyprice && newbuyprice.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return newbuyprice;
		}
    }

    /**
     * @param newbuyprice 
	 *            最新采购价
     */
    public void setNewbuyprice(BigDecimal newbuyprice) {
        this.newbuyprice = newbuyprice;
    }

    /**
     * @return 最新销售价
     */
    public BigDecimal getNewsaleprice() {
    	if(null!=newsaleprice && newsaleprice.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return newsaleprice;
		}
    }

    /**
     * @param newsaleprice 
	 *            最新销售价
     */
    public void setNewsaleprice(BigDecimal newsaleprice) {
        this.newsaleprice = newsaleprice;
    }

    /**
     * @return 最新库存价
     */
    public BigDecimal getNewstorageprice() {
    	if(null!=newstorageprice && newstorageprice.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return newstorageprice;
		}
    }

    /**
     * @param newstorageprice 
	 *            最新库存价
     */
    public void setNewstorageprice(BigDecimal newstorageprice) {
        this.newstorageprice = newstorageprice;
    }

    /**
     * @return 最新采购日期
     */
    public String getNewbuydate() {
        return newbuydate;
    }

    /**
     * @param newbuydate 
	 *            最新采购日期
     */
    public void setNewbuydate(String newbuydate) {
        this.newbuydate = newbuydate == null ? null : newbuydate.trim();
    }

    /**
     * @return 最新销售日期
     */
    public String getNewsaledate() {
        return newsaledate;
    }

    /**
     * @param newsaledate 
	 *            最新销售日期
     */
    public void setNewsaledate(String newsaledate) {
        this.newsaledate = newsaledate == null ? null : newsaledate.trim();
    }

    /**
     * @return 每单平均销量
     */
    public BigDecimal getEverybillaveragesales() {
    	if(null!=everybillaveragesales && everybillaveragesales.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return everybillaveragesales;
		}
    }

    /**
     * @param everybillaveragesales 
	 *            每单平均销量
     */
    public void setEverybillaveragesales(BigDecimal everybillaveragesales) {
        this.everybillaveragesales = everybillaveragesales;
    }

    /**
     * @return 最新入库日期
     */
    public String getNewinstroragedate() {
        return newinstroragedate;
    }

    /**
     * @param newinstroragedate 
	 *            最新入库日期
     */
    public void setNewinstroragedate(String newinstroragedate) {
        this.newinstroragedate = newinstroragedate == null ? null : newinstroragedate.trim();
    }

    /**
     * @return 最新出库日期
     */
    public String getNewoutstoragedate() {
        return newoutstoragedate;
    }

    /**
     * @param newoutstoragedate 
	 *            最新出库日期
     */
    public void setNewoutstoragedate(String newoutstoragedate) {
        this.newoutstoragedate = newoutstoragedate == null ? null : newoutstoragedate.trim();
    }

    /**
     * @return 最新库存
     */
    public BigDecimal getNewinventory() {
    	if(null!=newinventory && newinventory.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return newinventory;
		}
    }

    /**
     * @param newinventory 
	 *            最新库存
     */
    public void setNewinventory(BigDecimal newinventory) {
        this.newinventory = newinventory;
    }

    /**
     * @return 最新盘点日期
     */
    public String getNewcheckdate() {
        return newcheckdate;
    }

    /**
     * @param newcheckdate 
	 *            最新盘点日期
     */
    public void setNewcheckdate(String newcheckdate) {
        this.newcheckdate = newcheckdate == null ? null : newcheckdate.trim();
    }

    /**
     * @return 商品形状1方型2球型3圆柱4其他
     */
    public String getGshape() {
        return gshape;
    }

    /**
     * @param gshape 
	 *            商品形状1方型2球型3圆柱4其他
     */
    public void setGshape(String gshape) {
        this.gshape = gshape == null ? null : gshape.trim();
    }

    /**
     * @return 商品长度
     */
    public BigDecimal getGlength() {
        return glength;
    }

    /**
     * @param glength 
	 *            商品长度
     */
    public void setGlength(BigDecimal glength) {
        this.glength = glength;
    }

    /**
     * @return 长度转换因子（m）
     */
    public BigDecimal getGmlength() {
        return gmlength;
    }

    /**
     * @param gmlength 
	 *            长度转换因子（m）
     */
    public void setGmlength(BigDecimal gmlength) {
        this.gmlength = gmlength;
    }

    /**
     * @return 宽度
     */
    public BigDecimal getGwidth() {
        return gwidth;
    }

    /**
     * @param gwidth 
	 *            宽度
     */
    public void setGwidth(BigDecimal gwidth) {
        this.gwidth = gwidth;
    }

    /**
     * @return 宽度转换因子
     */
    public BigDecimal getGmwidth() {
        return gmwidth;
    }

    /**
     * @param gmwidth 
	 *            宽度转换因子
     */
    public void setGmwidth(BigDecimal gmwidth) {
        this.gmwidth = gmwidth;
    }

    /**
     * @return 高度
     */
    public BigDecimal getGhight() {
        return ghight;
    }

    /**
     * @param ghight 
	 *            高度
     */
    public void setGhight(BigDecimal ghight) {
        this.ghight = ghight;
    }

    /**
     * @return 高度转换因子
     */
    public BigDecimal getGmhight() {
        return gmhight;
    }

    /**
     * @param gmhight 
	 *            高度转换因子
     */
    public void setGmhight(BigDecimal gmhight) {
        this.gmhight = gmhight;
    }

    /**
     * @return 直径
     */
    public BigDecimal getGdiameter() {
        return gdiameter;
    }

    /**
     * @param gdiameter 
	 *            直径
     */
    public void setGdiameter(BigDecimal gdiameter) {
        this.gdiameter = gdiameter;
    }

    /**
     * @return 直径转换因子
     */
    public BigDecimal getGmdiameter() {
        return gmdiameter;
    }

    /**
     * @param gmdiameter 
	 *            直径转换因子
     */
    public void setGmdiameter(BigDecimal gmdiameter) {
        this.gmdiameter = gmdiameter;
    }

    /**
     * @return 毛重
     */
    public BigDecimal getGrossweight() {
        return grossweight;
    }

    /**
     * @param grossweight 
	 *            毛重
     */
    public void setGrossweight(BigDecimal grossweight) {
        this.grossweight = grossweight;
    }

    /**
     * @return 净重
     */
    public BigDecimal getNetweight() {
        return netweight;
    }

    /**
     * @param netweight 
	 *            净重
     */
    public void setNetweight(BigDecimal netweight) {
        this.netweight = netweight;
    }

    /**
     * @return 重量转换因子kg
     */
    public BigDecimal getKgweight() {
        return kgweight;
    }

    /**
     * @param kgweight 
	 *            重量转换因子kg
     */
    public void setKgweight(BigDecimal kgweight) {
        this.kgweight = kgweight;
    }

    /**
     * @return 照片
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image 
	 *            照片
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
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
        this.field01 = field01 == null ? null : field01.trim();
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
        this.field02 = field02 == null ? null : field02.trim();
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
        this.field03 = field03 == null ? null : field03.trim();
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
        this.field04 = field04 == null ? null : field04.trim();
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
        this.field05 = field05 == null ? null : field05.trim();
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
        this.field06 = field06 == null ? null : field06.trim();
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
        this.field07 = field07 == null ? null : field07.trim();
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
        this.field08 = field08 == null ? null : field08.trim();
    }

    /**
     * @return 自定义信息9
     */
    public String getField09() {
        return field09;
    }

    /**
     * @param field09 
	 *            自定义信息9
     */
    public void setField09(String field09) {
        this.field09 = field09 == null ? null : field09.trim();
    }

    /**
     * @return 建档人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            建档人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 建档人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 建档部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            建档部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 建档时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            建档时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 修改人用户编号
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 物理备注
     */
    public String getPhysicsremark() {
        return physicsremark;
    }

    /**
     * @param physicsremark 
	 *            物理备注
     */
    public void setPhysicsremark(String physicsremark) {
        this.physicsremark = physicsremark == null ? null : physicsremark.trim();
    }

	public String getDelMUIds() {
		return delMUIds;
	}

	public void setDelMUIds(String delMUIds) {
		this.delMUIds = delMUIds;
	}

	public String getDelPriceIds() {
		return delPriceIds;
	}

	public void setDelPriceIds(String delPriceIds) {
		this.delPriceIds = delPriceIds;
	}

	public String getDelStorageIds() {
		return delStorageIds;
	}

	public void setDelStorageIds(String delStorageIds) {
		this.delStorageIds = delStorageIds;
	}

	public String getDelWCIds() {
		return delWCIds;
	}

	public void setDelWCIds(String delWCIds) {
		this.delWCIds = delWCIds;
	}

	public String getDelSLIds() {
		return delSLIds;
	}

	public void setDelSLIds(String delSLIds) {
		this.delSLIds = delSLIds;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getGoodstypeName() {
		return goodstypeName;
	}

	public void setGoodstypeName(String goodstypeName) {
		this.goodstypeName = goodstypeName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDefaultsortName() {
		return defaultsortName;
	}

	public void setDefaultsortName(String defaultsortName) {
		this.defaultsortName = defaultsortName;
	}

	public String getBstypeName() {
		return bstypeName;
	}

	public void setBstypeName(String bstypeName) {
		this.bstypeName = bstypeName;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getStoragelocationName() {
		return storagelocationName;
	}

	public void setStoragelocationName(String storagelocationName) {
		this.storagelocationName = storagelocationName;
	}

	public String getShelflifeunitName() {
		return shelflifeunitName;
	}

	public void setShelflifeunitName(String shelflifeunitName) {
		this.shelflifeunitName = shelflifeunitName;
	}

	public String getChecktypeName() {
		return checktypeName;
	}

	public void setChecktypeName(String checktypeName) {
		this.checktypeName = checktypeName;
	}

	public String getCheckunitName() {
		return checkunitName;
	}

	public void setCheckunitName(String checkunitName) {
		this.checkunitName = checkunitName;
	}

	public String getDefaultbuyerName() {
		return defaultbuyerName;
	}

	public void setDefaultbuyerName(String defaultbuyerName) {
		this.defaultbuyerName = defaultbuyerName;
	}

	public String getDefaultsalerName() {
		return defaultsalerName;
	}

	public void setDefaultsalerName(String defaultsalerName) {
		this.defaultsalerName = defaultsalerName;
	}

	public String getDefaultsupplierName() {
		return defaultsupplierName;
	}

	public void setDefaultsupplierName(String defaultsupplierName) {
		this.defaultsupplierName = defaultsupplierName;
	}

	public String getDefaulttaxtypeName() {
		return defaulttaxtypeName;
	}

	public void setDefaulttaxtypeName(String defaulttaxtypeName) {
		this.defaulttaxtypeName = defaulttaxtypeName;
	}

	public String getGshapeName() {
		return gshapeName;
	}

	public void setGshapeName(String gshapeName) {
		this.gshapeName = gshapeName;
	}

	/**
	 * 采购总数量
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public BigDecimal getNewtotalbuynum() {
		if(null!=newtotalbuynum && newtotalbuynum.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return newtotalbuynum;
		}
	}

	/**
	 * 采购总数量
	 * @param newtotalbuynum
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public void setNewtotalbuynum(BigDecimal newtotalbuynum) {
		this.newtotalbuynum = newtotalbuynum;
	}

	/**
	 * 采购总金额
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public BigDecimal getNewtotalbuyamount() {
		if(null!=newtotalbuyamount && newtotalbuyamount.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return newtotalbuyamount;
		}
	}

	/**
	 * 采购总金额
	 * @param newtotalbuyamount
	 * @author zhanghonghui 
	 * @date 2013-7-13
	 */
	public void setNewtotalbuyamount(BigDecimal newtotalbuyamount) {
		this.newtotalbuyamount = newtotalbuyamount;
	}

	public String getEdittype() {
		return edittype;
	}

	public void setEdittype(String edittype) {
		this.edittype = edittype;
	}

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}

	public BigDecimal getTotalweight() {
		return totalweight;
	}

	public void setTotalweight(BigDecimal totalweight) {
		this.totalweight = totalweight;
	}

	public BigDecimal getTotalvolume() {
		return totalvolume;
	}

	public void setTotalvolume(BigDecimal totalvolume) {
		this.totalvolume = totalvolume;
	}

	public BigDecimal getSinglevolume() {
		return singlevolume;
	}

	public void setSinglevolume(BigDecimal singlevolume) {
		this.singlevolume = singlevolume;
	}

	public BigDecimal getCostaccountprice() {
		return costaccountprice;
	}

	public void setCostaccountprice(BigDecimal costaccountprice) {
		this.costaccountprice = costaccountprice;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public List<GoodsInfo_PriceInfo> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<GoodsInfo_PriceInfo> priceList) {
		this.priceList = priceList;
	}

	public String getProductfield() {
		return productfield;
	}

	public void setProductfield(String productfield) {
		this.productfield = productfield;
	}

	public String getOldbrand() {
		return oldbrand;
	}

	public void setOldbrand(String oldbrand) {
		this.oldbrand = oldbrand;
	}

	public String getOlddefaultsupplier() {
		return olddefaultsupplier;
	}

	public void setOlddefaultsupplier(String olddefaultsupplier) {
		this.olddefaultsupplier = olddefaultsupplier;
	}

	public String getBranddept() {
		return branddept;
	}

	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getAuxunitid() {
		return auxunitid;
	}

	public void setAuxunitid(String auxunitid) {
		this.auxunitid = auxunitid;
	}
	
	public BigDecimal getSlboxnum() {
        return slboxnum;
	}

	public void setSlboxnum(BigDecimal slboxnum) {
		this.slboxnum = slboxnum;
	}

	public String getAuxunitname() {
		return auxunitname;
	}

	public void setAuxunitname(String auxunitname) {
		this.auxunitname = auxunitname;
	}

	public BigDecimal getBuyboxprice() {
		return buyboxprice;
	}

	public void setBuyboxprice(BigDecimal buyboxprice) {
		this.buyboxprice = buyboxprice;
	}

	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public String getSecondsupplier() {
		return secondsupplier;
	}

	public void setSecondsupplier(String secondsupplier) {
		this.secondsupplier = secondsupplier;
	}

	public String getSecondsuppliername() {
		return secondsuppliername;
	}

	public void setSecondsuppliername(String secondsuppliername) {
		this.secondsuppliername = secondsuppliername;
	}

	public String getIsshelflifename() {
		return isshelflifename;
	}

	public void setIsshelflifename(String isshelflifename) {
		this.isshelflifename = isshelflifename;
	}

	public String getShelflifedetail() {
		return shelflifedetail;
	}

	public void setShelflifedetail(String shelflifedetail) {
		this.shelflifedetail = shelflifedetail;
	}

	public String getIsbatchname() {
		return isbatchname;
	}

	public void setIsbatchname(String isbatchname) {
		this.isbatchname = isbatchname;
	}

	public String getIsstoragelocationname() {
		return isstoragelocationname;
	}

	public void setIsstoragelocationname(String isstoragelocationname) {
		this.isstoragelocationname = isstoragelocationname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getMainunitName() {
		return mainunitName;
	}

	public void setMainunitName(String mainunitName) {
		this.mainunitName = mainunitName;
	}

	public String getImageids() {
		return imageids;
	}

	public void setImageids(String imageids) {
		this.imageids = imageids;
	}

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public BigDecimal getBoxprice1() {
        return boxprice1;
    }

    public void setBoxprice1(BigDecimal boxprice1) {
        this.boxprice1 = boxprice1;
    }

    public BigDecimal getBoxprice2() {
        return boxprice2;
    }

    public void setBoxprice2(BigDecimal boxprice2) {
        this.boxprice2 = boxprice2;
    }

    public BigDecimal getBoxprice3() {
        return boxprice3;
    }

    public void setBoxprice3(BigDecimal boxprice3) {
        this.boxprice3 = boxprice3;
    }

    public BigDecimal getBoxprice4() {
        return boxprice4;
    }

    public void setBoxprice4(BigDecimal boxprice4) {
        this.boxprice4 = boxprice4;
    }

    public BigDecimal getBoxprice5() {
        return boxprice5;
    }

    public void setBoxprice5(BigDecimal boxprice5) {
        this.boxprice5 = boxprice5;
    }

    public BigDecimal getBoxprice6() {
        return boxprice6;
    }

    public void setBoxprice6(BigDecimal boxprice6) {
        this.boxprice6 = boxprice6;
    }

    public BigDecimal getBoxprice7() {
        return boxprice7;
    }

    public void setBoxprice7(BigDecimal boxprice7) {
        this.boxprice7 = boxprice7;
    }

    public BigDecimal getBoxprice8() {
        return boxprice8;
    }

    public void setBoxprice8(BigDecimal boxprice8) {
        this.boxprice8 = boxprice8;
    }

    public BigDecimal getBoxprice9() {
        return boxprice9;
    }

    public void setBoxprice9(BigDecimal boxprice9) {
        this.boxprice9 = boxprice9;
    }

    public BigDecimal getBoxprice10() {
        return boxprice10;
    }

    public void setBoxprice10(BigDecimal boxprice10) {
        this.boxprice10 = boxprice10;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public String getJsgoodsid() {
        return jsgoodsid;
    }

    public void setJsgoodsid(String jsgoodsid) {
        this.jsgoodsid = jsgoodsid == null ? null : jsgoodsid.trim();
    }


    /**
     * @return 金税商品字段修改人用户编号
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public String getJsgoodsmodifyuserid() {
        return jsgoodsmodifyuserid;
    }

    /**
     * @param jsgoodsmodifyuserid
     *            金税商品字段修改人用户编号
     */
    public void setJsgoodsmodifyuserid(String jsgoodsmodifyuserid) {
        this.jsgoodsmodifyuserid = jsgoodsmodifyuserid == null ? null : jsgoodsmodifyuserid.trim();
    }

    /**
     * @return 金税商品字段修改人姓名
     */
    public String getJsgoodsmodifyusername() {
        return jsgoodsmodifyusername;
    }

    /**
     * @param jsgoodsmodifyusername
     *            金税商品字段修改人姓名
     */
    public void setJsgoodsmodifyusername(String jsgoodsmodifyusername) {
        this.jsgoodsmodifyusername = jsgoodsmodifyusername == null ? null : jsgoodsmodifyusername.trim();
    }

    /**
     * @return 金税商品字段修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getJsgoodsmodifytime() {
        return jsgoodsmodifytime;
    }

    /**
     * @param jsgoodsmodifytime
     *            金税商品字段修改时间
     */
    public void setJsgoodsmodifytime(Date jsgoodsmodifytime) {
        this.jsgoodsmodifytime = jsgoodsmodifytime;
    }


    /**
     * @return 金税税目编码
     */
    public String getJstaxsortid() {
        return jstaxsortid;
    }

    /**
     * @param jstaxsortid
     *            金税税目编码
     */
    public void setJstaxsortid(String jstaxsortid) {
        this.jstaxsortid = jstaxsortid == null ? null : jstaxsortid.trim();
    }

    public String getJstaxsortname() {
        return jstaxsortname;
    }

    public void setJstaxsortname(String jstaxsortname) {
        this.jstaxsortname = jstaxsortname == null ? null : jstaxsortname.trim();
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }
}