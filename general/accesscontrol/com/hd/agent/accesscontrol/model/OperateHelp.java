package com.hd.agent.accesscontrol.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单帮助类
 * Created by chenwei on 2017-03-28.
 */
public class OperateHelp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private int id;
    /**
     * 操作id
     */
    private String operateid;
    /**
     * url md5
     */
    private String md5url;
    /**
     * 标题
     */
    private String title;
    /**
     * 帮助内容
     */
    private String content;
    /**
     * 添加时间
     */
    private Date addtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperateid() {
        return operateid;
    }

    public void setOperateid(String operateid) {
        this.operateid = operateid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getMd5url() {
        return md5url;
    }

    public void setMd5url(String md5url) {
        this.md5url = md5url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
