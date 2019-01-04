package com.hd.agent.agprint.dao;

import com.hd.agent.agprint.model.PrintPaperSize;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by master on 2016/9/11.
 */
public interface PrintPaperSizeMapper {
    /**
     * 获取打印页面大小列表
     * @param pageMap
     * @return
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public List<PrintPaperSize> getPrintPaperSizePageList(PageMap pageMap);
    /**
     * 获取打印页面大小列表合计条数
     * @param pageMap
     * @return
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public int getPrintPaperSizePageCount(PageMap pageMap);
    /**
     * 添加打印页面大小
     * @param printPaperSize
     * @return
     * @author zhanghonghui
     * @date 2015年11月5日
     */
    public int insertPrintPaperSize(PrintPaperSize printPaperSize);
    /**
     * 更新打印页面大小
     * @param printPaperSize
     * @return
     * @author zhanghonghui
     * @date 2015年11月5日
     */
    public int updatePrintPaperSize(PrintPaperSize printPaperSize);
    /**
     * 删除一条打印页面大小
     * @param id
     * @return
     * @author zhanghonghui
     * @date 2015年11月5日
     */
    public int deletePrintPaperSize(@Param("id")String id);
    /**
     * 启用打印页面大小
     * @param printPaperSize
     * @return
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public int enablePrintPaperSize(PrintPaperSize printPaperSize);
    /**
     * 禁用打印页面大小
     * @param printPaperSize
     * @return
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public int disablePrintPaperSize(PrintPaperSize printPaperSize);
    /**
     * 根据编号获取打印页面大小
     * @param id
     * @return
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public PrintPaperSize getPrintPaperSize(@Param("id")String id);
    /**
     * 根据编号获取启用打印页面大小
     * @param id
     * @return
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public PrintPaperSize getEnablePrintPaperSize(@Param("id")String id);
    /**
     * 根据条件获取相关条数<br/>
     * Map中参数：<br/>
     * notequalid:与id不相等条件:<br/>
     * name：名称<br/>
     * width:长度<br/>
     * height:宽度<br/>
     * @param map
     * @return
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public int getPrintPaperSizeCountBy(Map map);
}
