/**
 * @(#)ExcelImportListener.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-12 zhengziyong 创建版本
 */
package com.hd.agent.common.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface ExcelImportListener {

	public void excelImport(List<Map<String, Object>> list);
	
}

