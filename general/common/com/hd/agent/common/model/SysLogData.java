/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-28 chenwei 创建版本
 */
package com.hd.agent.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 * @author chenwei
 */
public class SysLogData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键编号
     */
    private String id;
    /**
     * 修改前数据
     */
    private String olddata;
    /**
     * 修改后数据
     */
    private String newdata;
    /**
     * 添加时间
     */
    private Date addtime;

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewdata() {
        return newdata;
    }

    public void setNewdata(String newdata) {
        this.newdata = newdata;
    }

    public String getOlddata() {
        return olddata;
    }

    public void setOlddata(String olddata) {
        this.olddata = olddata;
    }
}