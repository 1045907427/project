package com.hd.agent.activiti.model;

import java.io.Serializable;

public class Source implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 流程定义ID(版本号)
     */
    private String definitionid;

    /**
     * 类型，xml、png、svg...
     */
    private String type;

    /**
     * 内容
     */
    private byte[] bytes;

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
     * @return 流程定义ID(版本号)
     */
    public String getDefinitionid() {
        return definitionid;
    }

    /**
     * @param definitionid 
	 *            流程定义ID(版本号)
     */
    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid == null ? null : definitionid.trim();
    }

    /**
     * @return 类型，xml、png、svg...
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            类型，xml、png、svg...
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 内容
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes 
	 *            内容
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}