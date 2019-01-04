/**
 * @(#)PageData.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-8 chenwei 创建版本
 */
package com.hd.agent.common.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果类
 * @author chenwei
 */
public class PageData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1393279090700760800L;
	/**
	 * 结果集
	 */
	private List list;
	/**
	 * 记录总数
	 */
	private int total = 0;
	/**
	 * 每页显示条数
	 */
	private int row = 10;
	/**
	 * 当前页数
	 */
	private int page = 1;
	/**
	 * 总页数
	 */
	private int totalPage = 1;
	/**
	 * 合计项
	 */
	private List footer;
	/**
	 * 初始化分页组件
	 * @param total
	 * @param list
	 * @param pageMap
	 */
	public PageData(int total,List list , PageMap pageMap) {
		setTotal(total);
		setList(list);
		setPage(pageMap.getPage());
		setrow(pageMap.getRows());
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getrow() {
		return row;
	}

	public void setrow(int row) {
		this.row = row;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		int p;
		if (total % row == 0) {
			p=total / row;
		} else {
			p=total / row + 1;
		}
		return p==0?1:p;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}
	
}

