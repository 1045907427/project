package com.hd.agent.agprint.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印任务明细
 */
public class PrintJobDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 打印编号
     */
    private String id;

    /**
     * 任务编号
     */
    private String jobid;
    /**
     * 打印的单据号
     */
    private String printorderid;


    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 当前页数
     */
    private Integer currentpage;

    /**
     * 总页数
     */
    private Integer totalpage;
    /**
     * 打印内容
     */
    private byte[] content;
    /**
     * 内容类型：内容类型："html","text","object"
     */
    private String contenttype;
    /**
     * 读取标志
     */
    private String readflag;

    /**
     * @return 打印编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            打印编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 任务编号
     */
    public String getJobid() {
        return jobid;
    }

    /**
     * @param jobid 
	 *            任务编号
     */
    public void setJobid(String jobid) {
        this.jobid = jobid == null ? null : jobid.trim();
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
     * @return 当前页数
     */
    public Integer getCurrentpage() {
        return currentpage;
    }

    /**
     * @param currentpage 
	 *            当前页数
     */
    public void setCurrentpage(Integer currentpage) {
        this.currentpage = currentpage;
    }

    /**
     * @return 总页数
     */
    public Integer getTotalpage() {
        return totalpage;
    }

    /**
     * @param totalpage 
	 *            总页数
     */
    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public String getPrintorderid() {
        return printorderid;
    }

    public void setPrintorderid(String printorderid) {
        this.printorderid = printorderid == null ? null : printorderid.trim();
    }
    /**
     * @return 打印内容
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content
     *            打印内容
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return 内容类型：内容类型："html","text","object"
     */
    public String getContenttype() {
        return contenttype;
    }

    /**
     * @param contenttype
     *            内容类型：内容类型："html","text","object"
     */
    public void setContenttype(String contenttype) {
        this.contenttype = contenttype == null ? null : contenttype.trim();
    }


    public String getReadflag() {
        return readflag;
    }

    public void setReadflag(String readflag) {
        this.readflag = readflag == null ? null : readflag.trim();
    }
}