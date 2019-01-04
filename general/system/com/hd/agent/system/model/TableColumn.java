/**
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-14 zhanghonghui 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 数据字典-表字段管理
 * @author zhanghonghui
 */
public class TableColumn implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6926565702386669348L;

	/**
     * 编号
     */
    private int id;

    /**
     * 表名
     */
    private String tablename;

    /**
     * 列名
     */
    private String columnname;

    /**
     * 列描述名
     */
    private String colchnname;

    /**
     * 数据类型
     */
    private String coldatatype;

    /**
     * 字段长度
     */
    private int colwidth;

    /**
     * 小数位数
     */
    private int coldecimal;

    /**
     * 列说明
     */
    private String coldescription;

    /**
     * 固定字段
     */
    private String usefixed;

    /**
     * 是否支持编码字段
     */
    private String usecoded;
    /**
     * 编码字段类型
     */
    private String codedcoltype;

    /**
     * 顺序
     */
    private int colorder;
    /**
     * 是否为主键1是0否
     */
    private String usepk;
    /**
     * 是否为NULL
     */
    private String usenull;

    /**
     * 初始值
     */
    private String coldefault;

    /**
     * 应用类型
     */
    private String colapplytype;

    /**
     * 是否支持数据权限
     */
    private String usedataprivilege;

    /**
     * 是否支持字段权限
     */
    private String usecolprivilege;

    /**
     * 是否可做查询条件
     */
    private String usecolquery;

    /**
     * 是否支持数据导出
     */
    private String usedataexport;

    /**
     * 是否支持参照窗口1是0否
     */
    private String usecolrefer;
    
    /**
     * 导出是否引用参照窗口
     */
    private String referwflag;
    /**
     * 参照窗口编号
     */
    private String referwid;
    /**
     * 参照窗口名称
     */
    private String wname;
    /**
     * 启用后是否可以修改1是0否
     */
    private String isopenedit;
    /**
     * 是否引用显示1是0否
     */
    private String isshow;
    /**
     * 创建时间
     */
    private Date adddate;

    /**
     * 创建人
     */
    private String adduserid;

    /**
     * 修改日期
     */
    private Date modifydate;

    /**
     * 修改人
     */
    private String modifyuserid;

    /**
     * 旧列名-更新时表结构时使用,不与数据库字段对应
     */
    private String oldcolumnname;
    
    /**
     * 是否必填
     */
    private String isrequired;
    
    /**
     * @return 编号
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return 表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename 
	 *            表名
     */
    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    /**
     * @return 列名
     */
    public String getColumnname() {
        return columnname;
    }

    /**
     * @param columnname 
	 *            列名
     */
    public void setColumnname(String columnname) {
        this.columnname = columnname == null ? null : columnname.trim();
    }

    /**
     * @return 列描述名
     */
    public String getColchnname() {
        return colchnname;
    }

    /**
     * @param colchnname 
	 *            列描述名
     */
    public void setColchnname(String colchnname) {
        this.colchnname = colchnname == null ? null : colchnname.trim();
    }

    /**
     * @return 数据类型
     */
    public String getColdatatype() {
        return coldatatype;
    }

    /**
     * @param coldatatype 
	 *            数据类型
     */
    public void setColdatatype(String coldatatype) {
        this.coldatatype = coldatatype == null ? null : coldatatype.trim();
    }

    /**
     * @return 字段长度
     */
    public int getColwidth() {
        return colwidth;
    }

    /**
     * @param colwidth 
	 *            字段长度
     */
    public void setColwidth(int colwidth) {
        this.colwidth = colwidth;
    }

    /**
     * @return 小数位数
     */
    public int getColdecimal() {
        return coldecimal;
    }

    /**
     * @param coldecimal 
	 *            小数位数
     */
    public void setColdecimal(int coldecimal) {
        this.coldecimal = coldecimal;
    }

    /**
     * @return 列说明
     */
    public String getColdescription() {
        return coldescription;
    }

    /**
     * @param coldescription 
	 *            列说明
     */
    public void setColdescription(String coldescription) {
        this.coldescription = coldescription == null ? null : coldescription.trim();
    }

    /**
     * @return 固定字段
     */
    public String getUsefixed() {
        return usefixed;
    }

    /**
     * @param usefixed 
	 *            固定字段
     */
    public void setUsefixed(String usefixed) {
        this.usefixed = usefixed == null ? null : usefixed.trim();
    }

    /**
     * @return 是否支持编码字段
     */
    public String getUsecoded() {
        return usecoded;
    }

    /**
     * @param usecoded 
	 *            是否支持编码字段
     */
    public void setUsecoded(String usecoded) {
        this.usecoded = usecoded == null ? null : usecoded.trim();
    }

    /**
     * 编码字段类型
     * @return
     * @author zhanghonghui 
     * @date 2013-1-12
     */
    public String getCodedcoltype() {
		return codedcoltype;
	}

    /**
     * 编码字段类型
     * @param codedcoltype
     * @author zhanghonghui 
     * @date 2013-1-12
     */
	public void setCodedcoltype(String codedcoltype) {
		this.codedcoltype = codedcoltype;
	}

	/**
     * @return 顺序
     */
    public int getColorder() {
        return colorder;
    }

    /**
     * @param colorder 
	 *            顺序
     */
    public void setColorder(int colorder) {
        this.colorder = colorder;
    }

    /**
     * @return 是否为NULL
     */
    public String getUsenull() {
        return usenull;
    }

    /**
     * @param usenull 
	 *            是否为NULL
     */
    public void setUsenull(String usenull) {
        this.usenull = usenull == null ? null : usenull.trim();
    }

    /**
     * @return 初始值
     */
    public String getColdefault() {
        return coldefault;
    }

    /**
     * @param coldefault 
	 *            初始值
     */
    public void setColdefault(String coldefault) {
        this.coldefault = coldefault == null ? null : coldefault.trim();
    }

    /**
     * @return 应用类型
     */
    public String getColapplytype() {
        return colapplytype;
    }

    /**
     * @param colapplytype 
	 *            应用类型
     */
    public void setColapplytype(String colapplytype) {
        this.colapplytype = colapplytype == null ? null : colapplytype.trim();
    }

    /**
     * @return 是否支持数据权限
     */
    public String getUsedataprivilege() {
        return usedataprivilege;
    }

    /**
     * @param usedataprivilege 
	 *            是否支持数据权限
     */
    public void setUsedataprivilege(String usedataprivilege) {
        this.usedataprivilege = usedataprivilege == null ? null : usedataprivilege.trim();
    }

    /**
     * @return 是否支持字段权限
     */
    public String getUsecolprivilege() {
        return usecolprivilege;
    }

    /**
     * @param usecolprivilege 
	 *            是否支持字段权限
     */
    public void setUsecolprivilege(String usecolprivilege) {
        this.usecolprivilege = usecolprivilege == null ? null : usecolprivilege.trim();
    }

    /**
     * @return 是否可做查询条件
     */
    public String getUsecolquery() {
        return usecolquery;
    }

    /**
     * @param usecolquery 
	 *            是否可做查询条件
     */
    public void setUsecolquery(String usecolquery) {
        this.usecolquery = usecolquery == null ? null : usecolquery.trim();
    }

    /**
     * @return 是否支持数据导出
     */
    public String getUsedataexport() {
        return usedataexport;
    }

    /**
     * @param usedataexport 
	 *            是否支持数据导出
     */
    public void setUsedataexport(String usedataexport) {
        this.usedataexport = usedataexport == null ? null : usedataexport.trim();
    }

    /**
     * @return 是否支持参照窗口1是0否
     */
    public String getUsecolrefer() {
        return usecolrefer;
    }

    /**
     * @param usecolrefer 
	 *            是否支持参照窗口1是0否
     */
    public void setUsecolrefer(String usecolrefer) {
        this.usecolrefer = usecolrefer == null ? null : usecolrefer.trim();
    }
    /**
     * @return 创建时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            创建时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    /**
     * @return 创建人
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            创建人
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 修改日期
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifydate() {
        return modifydate;
    }

    /**
     * @param modifydate 
	 *            修改日期
     */
    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
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
     * 旧列名-更新时表结构时使用,不与数据库字段对应
     * @return
     * @author zhanghonghui 
     * @date 2012-12-21
     */
	public String getOldcolumnname() {
		return oldcolumnname;
	}

	/**
	 * 旧列名-更新时表结构时使用,不与数据库字段对应
	 * @param oldcolumnname
	 * @author zhanghonghui 
	 * @date 2012-12-21
	 */
	public void setOldcolumnname(String oldcolumnname) {
		this.oldcolumnname = oldcolumnname;
	}

	public String getUsepk() {
		return usepk;
	}

	public void setUsepk(String usepk) {
		this.usepk = usepk;
	}

	public String getIsopenedit() {
		return isopenedit;
	}

	public void setIsopenedit(String isopenedit) {
		this.isopenedit = isopenedit;
	}

	public String getReferwid() {
		return referwid;
	}

	public void setReferwid(String referwid) {
		this.referwid = referwid;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	public String getReferwflag() {
		return referwflag;
	}

	public void setReferwflag(String referwflag) {
		this.referwflag = referwflag;
	}

	public String getIsrequired() {
		return isrequired;
	}

	public void setIsrequired(String isrequired) {
		this.isrequired = isrequired;
	}
	
}