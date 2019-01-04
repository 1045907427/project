package com.hd.agent.agprint.service;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;

import java.util.Map;

public interface IAgprintService {
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
    Map showPrintTempletByPrintQuery(Map map) throws Exception;
    /**
     * 添加打印任务
     * @param printJob
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年5月27日
     */
    boolean addPrintJob(PrintJob printJob) throws Exception;
    /**
     * 添加打印任务
     * @param printJobCallHandle
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年5月27日
     */
    boolean addPrintJobCallHandle(PrintJobCallHandle printJobCallHandle) throws Exception;

    /**
     * 设置打印模板里的公共参数
     * @param parameters
     * @return void
     * @throws
     * @author zhanghonghui
     * @date Oct 26, 2016
     */
    void setTempletCommonParameter(Map parameters) throws Exception;
}
