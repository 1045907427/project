package com.hd.agent.agprint.service;

import com.hd.agent.agprint.model.PrintPaperSize;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.Map;

/**
 * Created by master on 2016/9/11.
 */
public interface IPrintPaperSizeService {

    /**
     * 获取打印纸张页面大小列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public PageData showPrintPaperSizePageListData(PageMap pageMap) throws Exception;
    /**
     * 添加打印纸张页面大小
     * @param printPaperSize
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public Map addPrintPaperSize(PrintPaperSize printPaperSize) throws Exception;
    /**
     * 更新打印纸张页面大小
     * @param printPaperSize
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public Map updatePrintPaperSize(PrintPaperSize printPaperSize) throws Exception;
    /**
     * 删除打印纸张页面大小
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public Map deletePrintPaperSize(String id) throws Exception;
    /**
     * 批量删除打印纸张页面大小，多个编号以, 分隔
     * @param idarrs
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public Map deletePrintPaperSizeMore(String idarrs) throws Exception;

    /**
     * 启用打印纸张页面大小
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public Map enablePrintPaperSize(String id) throws Exception;
    /**
     * 禁用打印纸张页面大小
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public Map disablePrintPaperSize(String id) throws Exception;
    /**
     * 根据编号获取打印纸张页面大小
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月6日
     */
    public PrintPaperSize showPrintPaperSizeInfo(String id) throws Exception;
}
