package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.JsTaxTypeCode;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JsTaxTypeCodeMapper {
    /**
     * 获取金税税收分类编码列表数据
     * @param pageMap
     * @return java.util.List<com.hd.agent.basefiles.model.JsTaxTypeCode>
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public List<JsTaxTypeCode> getJsTaxTypeCodePageList(PageMap pageMap);
    /**
     * 获取金税税收分类编码列表合计
     * @param pageMap
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int getJsTaxTypeCodePageCount(PageMap pageMap);
    /**
     * 根据编码获取金税税收分类编码
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public JsTaxTypeCode getJsTaxTypeCodeById(@Param("id")String id);
    /**
     * 根据编码删除金税税收分类编码
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int deleteJsTaxTypeCodeById(@Param("id")String id);
    /**
     * 添加金税税收分类编码
     * @param jsTaxTypeCode
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int insertJsTaxTypeCode(JsTaxTypeCode jsTaxTypeCode);
    /**
     * 修改金税税收分类编码
     * @param jsTaxTypeCode
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int updateJsTaxTypeCode(JsTaxTypeCode jsTaxTypeCode);
    /**
     * 根据编码判断是否使用
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public int isUsedJsTaxTypeCodeById(@Param("id")String id);

    /**
     * 批量插入数据
     * @param list
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 29, 2017
     */
    public int insertJsTaxTypeCodeBatch(List<JsTaxTypeCode> list);

    /**
     * 获取金税税收分类与商品档案关联数据，返列表字段jstypeid,barcode
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Nov 29, 2017
     */
    public List<Map> getGoodsJsTypeCodeList();

    /**
     * 获取未关联金税税收分类商品列表
     * @param pageMap
     * @return java.util.List<java.util.Map>
     * @throws
     * @author zhanghonghui
     * @date Dec 01, 2017
     */
    public List<Map> getUnLinkJsTypeCodeGoodsPageList(PageMap pageMap);

    /**
     * 获取未关联金税税收分类商品条数
     * @param pageMap
     * @return java.util.List<java.util.Map>
     * @throws
     * @author zhanghonghui
     * @date Dec 01, 2017
     */
    public int getUnLinkJsTypeCodeGoodsPageCount(PageMap pageMap);


    /**
     * 获取已经关联金税税收分类商品列表
     * @param pageMap
     * @return java.util.List<java.util.Map>
     * @throws
     * @author zhanghonghui
     * @date Dec 01, 2017
     */
    public List<Map> getLinkJsTypeCodeGoodsPageList(PageMap pageMap);

    /**
     * 获取已经关联金税税收分类商品条数
     * @param pageMap
     * @return java.util.List<java.util.Map>
     * @throws
     * @author zhanghonghui
     * @date Dec 01, 2017
     */
    public int getLinkJsTypeCodeGoodsPageCount(PageMap pageMap);

}
