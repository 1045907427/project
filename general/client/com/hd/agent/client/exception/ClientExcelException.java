/**
 * @(#)ClientExcelException.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月4日 huangzhiqian 创建版本
 */
package com.hd.agent.client.exception;
/**
 * 
 * 门店Excel导入异常提醒
 * @author huangzhiqian
 */
public class ClientExcelException extends Exception{
	private static final long serialVersionUID = 1L;

	public ClientExcelException(String msg){
		super(msg);
	}
}

