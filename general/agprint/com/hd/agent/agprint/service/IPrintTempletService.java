/**
 * @(#)IagprintTempletService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-6 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface IPrintTempletService {
	/**
	 * 添加打印模板
	 * @param printTemplet
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map addPrintTemplet(PrintTemplet printTemplet) throws Exception;
	/**
	 * 更新打印模板,只更新模板名称、模板描述名，备注、修改人信息
	 * @param printTemplet
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map updatePrintTemplet(PrintTemplet printTemplet) throws Exception;
	/**
	 * 打印模板分页数据 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public PageData showPrintTempletPageListData(PageMap pageMap) throws Exception;
	/**
	 * 根据map中参数统计条数<br/>
	 * map中参数：code 模板名称<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-8
	 */
	public int showPrintTempletPageCount(Map map) throws Exception;
	/**
	 * 显示打印模板，并查询出打印模板关联的打印排序、打印模板资源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-8
	 */
	public PrintTemplet showPrintTemplet(String id) throws Exception;
	/**
	 * 只获取打印模板信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月14日
	 */
	public PrintTemplet showPurePrintTemplet(String id) throws Exception;
	
	/**
	 * 删除打印模板
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map deletePrintTemplet(String id) throws Exception;

	/**
	 * 打印模板文件设置默认 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map updatePrintTempletDefault(String id) throws Exception;

	/**
	 * 启用模板文件 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map enablePrintTemplet(String id) throws Exception;
	/**
	 * 禁用模板文件 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map disablePrintTemplet(String id) throws Exception;
	/**
	 * 获取打印模板路径<br/>
	 * map中参数：<br/>
	 * code： 模板代码(必填)<br/>
	 * realServerPath 真实服务器根目录所在路径(必填)<br/> 
	 * templetid： 指定模板编号<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-24
	 */
	public String showPrintTempletPathBy(Map map) throws Exception;
	/** 
	 * map中参数：<br/>
	 * state : 状态，1启用，0禁用<br/>
	 * code  : 代码 <br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014年12月15日
	 */
	public List getPrintTempletListBy(Map map) throws Exception;
	/**
	 * 打印时，获取打印模板<br/>
	 * map中参数：<br/>
	 * templetid： 指定模板编号<br/>
	 * notUseLinkType: 是否使用关联，当值为1时，theLinkType,theLinkData和模板代码中关联执行顺序失效
	 * theLinkType: 指定关联类型，有值，那么不再执行模板代码中关联执行顺序<br/>
	 * theLinkData: 指定关联数据<br/>
	 * linkCustomerid: 关联客户编号,用于模板代码中按客户指定关联的参数<br/>
	 * linkDeptid: 关联部门编号,用于模板代码中按部门指定关联的参数<br/>
	 * linkSalesarea: 关联销售区域,用于模板代码中按销售区域指定关联的参数<br/>
	 * linkEbshopwlgs: 关联电商物流公司,用于模板代码中按电商物流公司指定关联的参数<br/>
	 * code: 模板代码(必填)<br/>
	 * realServerPath: 真实服务器根目录所在路径(必填)<br/> 
	 * 返回值Map中参数<br/>
	 * printTempletFile: 打印模板文件<br/>
	 * printDataOrderSeq：打印内容
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月18日
	 */
	public Map showPrintTempletByPrintQuery(Map map) throws Exception;

	/**
	 * 设置打印模板里的公共参数
	 * @param parameters
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 26, 2016
	 */
	public void setTempletCommonParameter(Map parameters) throws Exception;

	/**
	 * 复制打印模板信息
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author zhanghonghui
	 * @date Jun 29, 2017
	 */
	Map addCopyPrintTemplet(String id) throws Exception;

}

