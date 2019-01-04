/**
 * @(#)Tree.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-24 chenwei 创建版本
 */
package com.hd.agent.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 树形结构
 * @author chenwei
 */
public class Tree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 是否展开
	 */
	private String open;
	
	/**
	 * 是否选中
	 */
	private String checked;

	private boolean chkDisabled;
	
	/**
	 * @return 是否选中
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public String getChecked() {
		return checked;
	}

	/**
	 * @param checked 是否选中
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}

	/**
	 * @return 是否展开
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public String getOpen() {
		return open;
	}

	/**
	 * @param open 是否展开
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public void setOpen(String open) {
		this.open = open;
	}

	/**
	 * 状态
	 * @return 
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @param state 状态
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return 键名
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id 键名
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return 显示名称
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text 显示名称
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return 父节点
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public String getParentid() {
		return parentid;
	}
	
	
	/**
	 * @param parentid 父节点
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	/**
	 * @return 子结点
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public List getChildren() {
		return children;
	}
	
	/**
	 * @param children 子结点
	 * @author panxiaoxiao 
	 * @date 2013-4-10
	 */
	public void setChildren(List children) {
		this.children = children;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
}

