package com.hd.agent.agprint.dao;

import com.hd.agent.agprint.model.PrintJobDetailImage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by master on 2016/11/7.
 */
public interface PrintJobDetailImageMapper {
    /**
     * 添加打印的页内容图片
     * @date 2016-06-29
     * @author zhanghonghui
     * @param printJobDetailImage
     * @return
     */
    public int insertPrintJobDetailImage(PrintJobDetailImage printJobDetailImage);

    /**
     * 获取打印的页内容图片
     * @date 2016-06-29
     * @author zhanghonghui
     * @param id
     * @return
     */
    public PrintJobDetailImage getPrintJobDetailImage(@Param("id") String id);

    /**
     * 删除打印页内容图片
     * @param id
     * @author zhanghonghui
     * @date 2016年5月27日
     * @return
     */
    public int deletePrintJobDetailImage(@Param("id") String id);
    /**
     * 批量删除date之前的打印任务内容图片数据
     * @param date
     * @return
     */
    public int deletePrintJobDetailImageBeforeDate(@Param("date") Date date);

    /**
     * 获取打印内容图片列表
     * @param map
     * @return List<PrintJobDetailImage>
     * @throws
     * @author zhanghonghui
     * @date Oct 22, 2017
     */
    public List<PrintJobDetailImage> getPrintJobDetailImageListBy(Map map);
    /**
     * 获取打印内容图片文件序列
     * @return
     * @throws
     * @author zhanghonghui
     * @date Oct 22, 2017
     */
    public String getPrintJobDetailImageFileId();
}
