package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.JsTaxTypeCode;
import com.hd.agent.basefiles.model.JsTaxTypeCodeBarcode;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JsTaxTypeCodeBarcodeMapper {
    /**
     * 获取税收分类条形码列表数据
     * @param pageMap
     * @return java.util.List<com.hd.agent.basefiles.model.JsTaxTypeCode>
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public List<Map> getJsTaxTypeCodeBarcodePageList(PageMap pageMap);
    /**
     * 获取税收分类条形码列表合计
     * @param pageMap
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int getJsTaxTypeCodeBarcodePageCount(PageMap pageMap);
    /**
     * 根据编码获取税收分类条形码
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public JsTaxTypeCodeBarcode getJsTaxTypeCodeBarcodeById(@Param("id") String id);
    /**
     * 根据编码删除税收分类条形码
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int deleteJsTaxTypeCodeBarcode(@Param("id") String id);
    /**
     * 根据编码删除税收分类条形码
     * @param jstypeid
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int deleteJsTaxTypeCodeBarcodeByJsId(@Param("jstypeid") String jstypeid);

    /**
     * 添加税收分类条形码
     * @param jsTaxTypeCodeBarcode
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int insertJsTaxTypeCodeBarcode(JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode);
    /**
     * 修改税收分类条形码
     * @param jsTaxTypeCodeBarcode
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int updateJsTaxTypeCodeBarcode(JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode);

    /**
     * 根据map中参数，计算存储条数<br/>
     * map中参数：<br/>
     * jstypeid:税收分类编码<br/>
     * barcode:条形码<br/>
     * notCurId:非当前编码<br/>
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public int getJsTaxTypeCodeBarcodeCountBy(Map map);

    /**
     * 批量插入数据
     * @param list
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 29, 2017
     */
    public int insertJsTaxTypeCodeBarcodeBatch(List<JsTaxTypeCodeBarcode> list);

    /**
     * 获取图税收分类、条形码、商品编码
     * @param
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 29, 2017
     */
    public List<Map> getJsTaxTypeCodeBarcodeGoodsList();


}
