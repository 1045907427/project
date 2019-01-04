package com.hd.agent.basefiles.service;

import com.hd.agent.basefiles.model.JsTaxTypeCode;
import com.hd.agent.basefiles.model.JsTaxTypeCodeBarcode;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

public interface IHtGoldTaxSystemService {
    /**
     * 获取金税税收分类编码分页列表数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public PageData getJsTaxTypeCodePageListData(PageMap pageMap) throws Exception;
    /**
     * 新增金税税收分类编码
     * @param jsTaxTypeCode
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public Map addJsTaxTypeCode(JsTaxTypeCode jsTaxTypeCode) throws Exception;
    /**
     * 编辑金税税收分类编码
     * @param jsTaxTypeCode
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public boolean editJsTaxTypeCode(JsTaxTypeCode jsTaxTypeCode) throws Exception;
    /**
     * 删除税收分类
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public Map deleteJsTaxTypeCode(String id) throws Exception;
    /**
     * 根据编码获取金税税收分类编码
     * @param id
     * @return JsTaxTypeCode
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public JsTaxTypeCode getJsTaxTypeCodeById(String id) throws Exception;
    /**
     * 根据编码是否已经使用
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public boolean isUsedJsTaxTypeCodeById(String id) throws Exception;

    /**
     * 导入金税税收分类数据
     * @param dataList
     * @param optionMap
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Nov 29, 2017
     */
    public Map importJsTaxTypeCodeData(List<Map<String,Object>> dataList,Map optionMap) throws Exception;

    /**
     * 添加关联金税税收分类
     * @param map
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Dec 03, 2017
     */
    public Map addLinkJsTypeCode(Map map) throws Exception;
    
    
    /*===================================税收分类关联商品条形码表============================================================*/

    /**
     * 获取税收分类关联商品条形码分页列表数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public PageData getJsTaxTypeCodeBarcodePageListData(PageMap pageMap) throws Exception;
    /**
     * 新增税收分类关联商品条形码
     * @param jsTaxTypeCodeBarcode
     * @return map
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public Map addJsTaxTypeCodeBarcode(JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode) throws Exception;
    /**
     * 编辑税收分类关联商品条形码
     * @param jsTaxTypeCodeBarcode
     * @return map
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public Map editJsTaxTypeCodeBarcode(JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode) throws Exception;
    /**
     * 删除税收分类关联商品条形码
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public boolean deleteJsTaxTypeCodeBarcode(String id) throws Exception;
    /**
     * 根据编码获取税收分类关联商品条形码
     * @param id
     * @return JsTaxTypeCodeBarcode
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public JsTaxTypeCodeBarcode getJsTaxTypeCodeBarcodeById(String id) throws Exception;
    /**
     * 根据税收分类编码和条形码，计算存储条数
     * @param jstypeid
     * @param barcode
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public int getJsTaxTypeCodeBarcodeCount(String jstypeid,String barcode) throws Exception;
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
    public int getJsTaxTypeCodeBarcodeCountBy(Map map) throws Exception;

    /**
     * 导入税收分类关联商品条形码数据
     * @param dataList
     * @param optionMap
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Nov 29, 2017
     */
    public Map importJsTaxTypeCodeBarcodeData(List<Map<String,Object>> dataList,Map optionMap) throws Exception;
    /**
     * 使用条形码匹配商品档案里金税编码
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Nov 29, 2017
     */
    public Map updateAndLinkGoodsInfoJsCode() throws Exception;

    /**
     * 根据商品档案中金税税收分类编码与条形码生成关联数据
     * @param
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Dec 01, 2017
     */
    public Map updateAndCreateJsBarcodeData() throws Exception;

    /**
     * 获取未关联金税税收分类商品列表
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public PageData getUnLinkJsTypeCodeGoodsPageListData(PageMap pageMap) throws Exception;

    /**
     * 获取已经关联金税税收分类商品列表
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author zhanghonghui
     * @date Nov 28, 2017
     */
    public PageData getLinkJsTypeCodeGoodsPageListData(PageMap pageMap) throws Exception;

}
