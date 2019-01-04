package com.hd.agent.activiti.model;

import java.io.Serializable;

public class HandlerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 开始handler
     */
    private String handler;

    /**
     * 开始handler路径com.xxx.xxx.
     */
    private String clazz;

    /**
     * 开始handler描述
     */
    private String handlerdescription;

    /**
     * 备注
     */
    private String remark;

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
     * @return 开始handler
     */
    public String getHandler() {
        return handler;
    }

    /**
     * @param handler 
	 *            开始handler
     */
    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    /**
     * @return 开始handler路径com.xxx.xxx.
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz 
	 *            开始handler路径com.xxx.xxx.
     */
    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    /**
     * @return 开始handler描述
     */
    public String getHandlerdescription() {
        return handlerdescription;
    }

    /**
     * @param handlerdescription 
	 *            开始handler描述
     */
    public void setHandlerdescription(String handlerdescription) {
        this.handlerdescription = handlerdescription == null ? null : handlerdescription.trim();
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
}