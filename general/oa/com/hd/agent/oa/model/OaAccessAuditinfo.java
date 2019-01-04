package com.hd.agent.oa.model;

import java.io.Serializable;
import java.util.Date;

public class OaAccessAuditinfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 节点id
     */
    private String nodeid;

    /**
     * 节点名称
     */
    private String nodename;

    /**
     * 审核人名称
     */
    private String auditname;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 节点顺序
     */
    private Integer seq;

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
     * @return 节点id
     */
    public String getNodeid() {
        return nodeid;
    }

    /**
     * @param nodeid 
	 *            节点id
     */
    public void setNodeid(String nodeid) {
        this.nodeid = nodeid == null ? null : nodeid.trim();
    }

    /**
     * @return 节点名称
     */
    public String getNodename() {
        return nodename;
    }

    /**
     * @param nodename 
	 *            节点名称
     */
    public void setNodename(String nodename) {
        this.nodename = nodename == null ? null : nodename.trim();
    }

    /**
     * @return 审核人名称
     */
    public String getAuditname() {
        return auditname;
    }

    /**
     * @param auditname 
	 *            审核人名称
     */
    public void setAuditname(String auditname) {
        this.auditname = auditname == null ? null : auditname.trim();
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
     * @return 节点顺序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            节点顺序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}