package com.hd.agent.agprint.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印内容图片
 */
public class PrintJobDetailImage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String id;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 任务编号
     */
    private String jobid;

    /**
     * 内容编号
     */
    private String detailid;
    /**
     * 图片名字
     */
    private String name;
    /**
     * 图片内容
     */
    private byte[] content;
    /**
     * 文件保存路径
     */
    private String fullpath;

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
     * @return 内容编号
     */
    public String getDetailid() {
        return detailid;
    }

    /**
     * @param detailid 
	 *            内容编号
     */
    public void setDetailid(String detailid) {
        this.detailid = detailid == null ? null : detailid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 图片内容
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content 
	 *            图片内容
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath == null ? null : fullpath.trim();
    }
}