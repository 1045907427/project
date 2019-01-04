/**
 * @(#)TableDataDict.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-7 zhanghonghui 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class TableDataDict implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1409929936931840414L;
	/**
	 * 键名
	 */
	private String id;
	/**
	 * 显示名称
	 */
	private String text;
	/**
	 * 父节点
	 */
	private String parentid;
	/**
	 * 子结点
	 */
	private List children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	
}

