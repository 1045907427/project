/**
 * @(#)AgReportprint.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-8-20 zhanghonghui 创建版本
 */
package com.hd.agent.common.action;


/**
 * 
 * 
 * @author zhanghonghui
 */
public class AgReportprintAction extends BaseAction {
	/**
	 * 打印预览页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-8-20
	 */
	public String agReportprintViewPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 弹出新页面预览页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-5
	 */
	public String agReportprintPopViewPage() throws Exception{
		String urlparam=request.getParameter("urlparam");
		request.setAttribute("urlparam", urlparam);
		return SUCCESS;
	}
	
}

