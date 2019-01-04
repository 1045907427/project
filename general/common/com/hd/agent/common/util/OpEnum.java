/**
 * @(#)OpEnum.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-24 chenwei 创建版本
 */
package com.hd.agent.common.util;
/**
 * 
 * sql查询条件枚举
 * @author chenwei
 */
public enum OpEnum {
	/**
	 * equal: 相等,notequal: 不相等,startwith: 以..开始,'+
     * endwith: 以..结束,like: 相似,greater: 大于,greaterorequal: 大于或等于,less: 小于,
     * lessorequal: 小于或等于,in: 包括在...,notin: 不包括...
	 */
	equal, notequal, startwith, endwith, like, greater, greaterorequal, less, lessorequal, in, notin;
	
	/**
	 * 根据字符串获取枚举属性
	 * @param op
	 * @return
	 * @author chenwei 
	 * @date 2012-12-24
	 */
	public static OpEnum getOpEnum(String op) {
		return valueOf(op.toLowerCase());
	}
}

