package com.hd.agent.basefiles.service;

import com.hd.agent.basefiles.model.StorageItemGoods;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2017/11/21.
 */
public interface IStorageItemService {
//    /**
//     * 获取仓库货位
//     * @param pageMap
//     * @return com.hd.agent.common.util.PageData
//     * @throws
//     * @author luoqiang
//     * @date Nov 22, 2017
//     */
//    public PageData getStorageItemList(PageMap pageMap) throws Exception;

    /**
     * 保存仓库货位
     * @param storageItemGoods
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public Map addStorageItemGoods(StorageItemGoods storageItemGoods);

    /**
     * 根据商品编码和仓库获取仓库货位档案
     * @param goodsid
     * @param storageid
     * @return com.hd.agent.basefiles.model.StorageItem
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public StorageItemGoods getStorageItemGoods(String goodsid, String storageid);

    /**
     * 保存仓库货位
     * @param storageItemGoods
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public Map editStorageItemGoods(StorageItemGoods storageItemGoods);

//    /**
//     * 判断仓库货位编码是否重复
//     * @param id
//     * @return java.lang.Boolean
//     * @throws
//     * @author luoqiang
//     * @date Nov 22, 2017
//     */
//    public Boolean isRepeatStorageItemID(String id);
//
//    /**
//     * 判断名称是否在仓库中
//     * @param name
//     * @param storageid
//     * @return java.lang.Boolean
//     * @throws
//     * @author luoqiang
//     * @date Nov 22, 2017
//     */
//    public Boolean isRepeatStorageItemName(String name,String storageid);

    /**
     * 获取仓库下的商品货位
     * @param
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public PageData showStorageItemGoodsList(PageMap pageMap) throws Exception;

    /**
     * 删除仓库商品货位
     * @param storageid
     * @param idstr
     * @return Map
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public Map deleteStorageItemGoods(String storageid, String idstr);

    /**
     * 导入仓库商品货位
     * @param list
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public List<Map<String, Object>> importStorageItemGoods(List list) throws Exception;

    /**
     * 获取仓库商品货位的仓库信息
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Nov 27, 2017
     */
    public PageData showStorageInfoList(PageMap pageMap) throws Exception;

    /**
     * 根据商品和仓库获取商品货位
     * @param goodsid
     * @param storageid
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 28, 2017
     */
    public String getStorageGoodsItem(String goodsid, String storageid) throws Exception;

}
