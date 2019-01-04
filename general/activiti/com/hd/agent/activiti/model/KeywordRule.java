package com.hd.agent.activiti.model;

import java.io.Serializable;

public class KeywordRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 流程标识符
     */
    private String definitionkey;

    /**
     * 流程 表单/URL
     */
    private String definitionset;

    /**
     * 流程对应表(针对URL表单)
     */
    private String definitiontable;

    /**
     * 关键字1
     */
    private String keyword1;

    /**
     * 关键字2
     */
    private String keyword2;

    /**
     * 关键字3
     */
    private String keyword3;

    /**
     * 关键字4
     */
    private String keyword4;

    /**
     * 关键字5
     */
    private String keyword5;

    /**
     * 添加用户编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private String addtime;

    /**
     * 流程定义ID(版本号)
     */
    private String definitionid;

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
     * @return 流程标识符
     */
    public String getDefinitionkey() {
        return definitionkey;
    }

    /**
     * @param definitionkey 
	 *            流程标识符
     */
    public void setDefinitionkey(String definitionkey) {
        this.definitionkey = definitionkey == null ? null : definitionkey.trim();
    }

    /**
     * @return 流程 表单/URL
     */
    public String getDefinitionset() {
        return definitionset;
    }

    /**
     * @param definitionset 
	 *            流程 表单/URL
     */
    public void setDefinitionset(String definitionset) {
        this.definitionset = definitionset == null ? null : definitionset.trim();
    }

    /**
     * @return 关键字1
     */
    public String getKeyword1() {
        return keyword1;
    }

    /**
     * @param keyword1 
	 *            关键字1
     */
    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1 == null ? null : keyword1.trim();
    }

    /**
     * @return 关键字2
     */
    public String getKeyword2() {
        return keyword2;
    }

    /**
     * @param keyword2 
	 *            关键字2
     */
    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2 == null ? null : keyword2.trim();
    }

    /**
     * @return 关键字3
     */
    public String getKeyword3() {
        return keyword3;
    }

    /**
     * @param keyword3 
	 *            关键字3
     */
    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3 == null ? null : keyword3.trim();
    }

    /**
     * @return 关键字4
     */
    public String getKeyword4() {
        return keyword4;
    }

    /**
     * @param keyword4 
	 *            关键字4
     */
    public void setKeyword4(String keyword4) {
        this.keyword4 = keyword4 == null ? null : keyword4.trim();
    }

    /**
     * @return 关键字5
     */
    public String getKeyword5() {
        return keyword5;
    }

    /**
     * @param keyword5 
	 *            关键字5
     */
    public void setKeyword5(String keyword5) {
        this.keyword5 = keyword5 == null ? null : keyword5.trim();
    }

    /**
     * @return 添加用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加时间
     */
    public String getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getDefinitiontable() {
        return definitiontable;
    }

    public void setDefinitiontable(String definitiontable) {
        this.definitiontable = definitiontable;
    }

    public String getDefinitionid() {
        return definitionid;
    }

    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }
}