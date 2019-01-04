package com.hd.agent.system.model;

import java.io.Serializable;
/**
 * 参照窗口列信息
 * @author chenwei
 */
public class ReferWindowColumn implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 参照窗口编号
     */
    private String referwinid;

    /**
     * 字段
     */
    private String col;

    /**
     * 字段名称
     */
    private String colname;

    /**
     * 对应表名
     */
    private String tablename;

    /**
     * 对应表字段
     */
    private String tablecol;

    /**
     * 引用情况1主键2文本框显示3主键且显示4不显示5父节点6下拉显示0窗口显示
     */
    private String isquote;

    /**
     * 编码类型(为空时表示不是编码类型)
     */
    private String codetype;

    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer seq;
    /**
     * 宽度
     */
    private Integer width;
    /**
     * 字段排序
     */
    private String orderbyseq;
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
     * @return 参照窗口编号
     */
    public String getReferwinid() {
        return referwinid;
    }

    /**
     * @param referwinid 
	 *            参照窗口编号
     */
    public void setReferwinid(String referwinid) {
        this.referwinid = referwinid == null ? null : referwinid.trim();
    }

    /**
     * @return 字段
     */
    public String getCol() {
        return col;
    }

    /**
     * @param col 
	 *            字段
     */
    public void setCol(String col) {
        this.col = col == null ? null : col.trim();
    }

    /**
     * @return 字段名称
     */
    public String getColname() {
        return colname;
    }

    /**
     * @param colname 
	 *            字段名称
     */
    public void setColname(String colname) {
        this.colname = colname == null ? null : colname.trim();
    }

    /**
     * @return 对应表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename 
	 *            对应表名
     */
    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    /**
     * @return 对应表字段
     */
    public String getTablecol() {
        return tablecol;
    }

    /**
     * @param tablecol 
	 *            对应表字段
     */
    public void setTablecol(String tablecol) {
        this.tablecol = tablecol == null ? null : tablecol.trim();
    }

    /**
     * @return 引用情况1主键2文本框显示3主键且显示4不显示5父节点6下拉显示0窗口显示
     */
    public String getIsquote() {
        return isquote;
    }

    /**
     * @param isquote 
	 *           引用情况1主键2文本框显示3主键且显示4不显示5父节点6下拉显示0窗口显示
     */
    public void setIsquote(String isquote) {
        this.isquote = isquote == null ? null : isquote.trim();
    }

    /**
     * @return 编码类型(为空时表示不是编码类型)
     */
    public String getCodetype() {
        return codetype;
    }

    /**
     * @param codetype 
	 *            编码类型(为空时表示不是编码类型)
     */
    public void setCodetype(String codetype) {
        this.codetype = codetype == null ? null : codetype.trim();
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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getOrderbyseq() {
		return orderbyseq;
	}

	public void setOrderbyseq(String orderbyseq) {
        this.orderbyseq = orderbyseq == null ? null : orderbyseq.trim();
	}
    
}