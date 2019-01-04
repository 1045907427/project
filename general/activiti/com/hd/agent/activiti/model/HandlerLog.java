package com.hd.agent.activiti.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class HandlerLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 流程标识
     */
    private String definitionkey;

    /**
     * 流程版本
     */
    private String definitionid;

    /**
     * 工作编号
     */
    private String processid;

    /**
     * 节点标识
     */
    private String taskkey;

    /**
     * 节点名称
     */
    private String taskname;

    /**
     * 事件名称 create： ；complete：；
     */
    private String event;

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
     * 执行人
     */
    private String loguserid;

    /**
     * 执行人名称
     */
    private String logusername;

    /**
     * 执行时间
     */
    private Date logtime;

    /**
     * 参数1类
     */
    private String param1clazz;

    /**
     * 参数2类
     */
    private String param2clazz;

    /**
     * 参数3类
     */
    private String param3clazz;

    /**
     * 参数4类
     */
    private String param4clazz;

    /**
     * 参数5类
     */
    private String param5clazz;

    /**
     * 返回值类
     */
    private String returnobjclazz;

    /**
     * 备注
     */
    private String remark;

    /**
     * 参数1
     */
    private byte[] param1;

    /**
     * 参数2
     */
    private byte[] param2;

    /**
     * 参数3
     */
    private byte[] param3;

    /**
     * 参数4
     */
    private byte[] param4;

    /**
     * 参数5
     */
    private byte[] param5;

    /**
     * 返回值
     */
    private byte[] returnobj;

    /**
     * taskid
     */
    private String taskid;

    /**
     * 工作标题
     */
    private String processname;

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
     * @return 流程标识
     */
    public String getDefinitionkey() {
        return definitionkey;
    }

    /**
     * @param definitionkey
     *            流程标识
     */
    public void setDefinitionkey(String definitionkey) {
        this.definitionkey = definitionkey == null ? null : definitionkey.trim();
    }

    /**
     * @return 流程版本
     */
    public String getDefinitionid() {
        return definitionid;
    }

    /**
     * @param definitionid
     *            流程版本
     */
    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid == null ? null : definitionid.trim();
    }

    /**
     * @return 工作编号
     */
    public String getProcessid() {
        return processid;
    }

    /**
     * @param processid
     *            工作编号
     */
    public void setProcessid(String processid) {
        this.processid = processid == null ? null : processid.trim();
    }

    /**
     * @return 节点标识
     */
    public String getTaskkey() {
        return taskkey;
    }

    /**
     * @param taskkey
     *            节点标识
     */
    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey == null ? null : taskkey.trim();
    }

    /**
     * @return 节点名称
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * @param taskname
     *            节点名称
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname == null ? null : taskname.trim();
    }

    /**
     * @return 事件名称 create： ；complete：；
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event
     *            事件名称 create： ；complete：；
     */
    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
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
     * @return 执行人
     */
    public String getLoguserid() {
        return loguserid;
    }

    /**
     * @param loguserid
     *            执行人
     */
    public void setLoguserid(String loguserid) {
        this.loguserid = loguserid == null ? null : loguserid.trim();
    }

    /**
     * @return 执行人名称
     */
    public String getLogusername() {
        return logusername;
    }

    /**
     * @param logusername
     *            执行人名称
     */
    public void setLogusername(String logusername) {
        this.logusername = logusername == null ? null : logusername.trim();
    }

    /**
     * @return 执行时间
     */
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getLogtime() {
        return logtime;
    }

    /**
     * @param logtime
     *            执行时间
     */
    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    /**
     * @return 参数1类
     */
    public String getParam1clazz() {
        return param1clazz;
    }

    /**
     * @param param1clazz
     *            参数1类
     */
    public void setParam1clazz(String param1clazz) {
        this.param1clazz = param1clazz == null ? null : param1clazz.trim();
    }

    /**
     * @return 参数2类
     */
    public String getParam2clazz() {
        return param2clazz;
    }

    /**
     * @param param2clazz
     *            参数2类
     */
    public void setParam2clazz(String param2clazz) {
        this.param2clazz = param2clazz == null ? null : param2clazz.trim();
    }

    /**
     * @return 参数3类
     */
    public String getParam3clazz() {
        return param3clazz;
    }

    /**
     * @param param3clazz
     *            参数3类
     */
    public void setParam3clazz(String param3clazz) {
        this.param3clazz = param3clazz == null ? null : param3clazz.trim();
    }

    /**
     * @return 参数4类
     */
    public String getParam4clazz() {
        return param4clazz;
    }

    /**
     * @param param4clazz
     *            参数4类
     */
    public void setParam4clazz(String param4clazz) {
        this.param4clazz = param4clazz == null ? null : param4clazz.trim();
    }

    /**
     * @return 参数5类
     */
    public String getParam5clazz() {
        return param5clazz;
    }

    /**
     * @param param5clazz
     *            参数5类
     */
    public void setParam5clazz(String param5clazz) {
        this.param5clazz = param5clazz == null ? null : param5clazz.trim();
    }

    /**
     * @return 返回值类
     */
    public String getReturnobjclazz() {
        return returnobjclazz;
    }

    /**
     * @param returnobjclazz
     *            返回值类
     */
    public void setReturnobjclazz(String returnobjclazz) {
        this.returnobjclazz = returnobjclazz == null ? null : returnobjclazz.trim();
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
     * @return 参数1
     */
    public byte[] getParam1() {
        return param1;
    }

    /**
     * @param param1
     *            参数1
     */
    public void setParam1(byte[] param1) {
        this.param1 = param1;
    }

    /**
     * @return 参数2
     */
    public byte[] getParam2() {
        return param2;
    }

    /**
     * @param param2
     *            参数2
     */
    public void setParam2(byte[] param2) {
        this.param2 = param2;
    }

    /**
     * @return 参数3
     */
    public byte[] getParam3() {
        return param3;
    }

    /**
     * @param param3
     *            参数3
     */
    public void setParam3(byte[] param3) {
        this.param3 = param3;
    }

    /**
     * @return 参数4
     */
    public byte[] getParam4() {
        return param4;
    }

    /**
     * @param param4
     *            参数4
     */
    public void setParam4(byte[] param4) {
        this.param4 = param4;
    }

    /**
     * @return 参数5
     */
    public byte[] getParam5() {
        return param5;
    }

    /**
     * @param param5
     *            参数5
     */
    public void setParam5(byte[] param5) {
        this.param5 = param5;
    }

    /**
     * @return 返回值
     */
    public byte[] getReturnobj() {
        return returnobj;
    }

    /**
     * @param returnobj
     *            返回值
     */
    public void setReturnobj(byte[] returnobj) {
        this.returnobj = returnobj;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }
}