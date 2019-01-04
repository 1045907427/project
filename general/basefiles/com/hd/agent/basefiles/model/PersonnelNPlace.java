package com.hd.agent.basefiles.model;

import java.io.Serializable;

public class PersonnelNPlace implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 籍贯编码
     */
    private String id;

    /**
     * 籍贯名称
     */
    private String name;

    /**
     * @return 籍贯编码
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            籍贯编码
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 籍贯名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            籍贯名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}