package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.StorageItemGoods;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luoq on 2017/11/21.
 */
public interface StorageItemMapper {
    /**
     * 获取仓库货位
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public List getStorageItemGoodsList(PageMap pageMap);

    /**
     * 获取仓库货位
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public int getStorageItemGoodsCount(PageMap pageMap);

    /**
     * 添加商品货位
     * @param storageItemGoods
     * @return int
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public int addStorageItemGoods(StorageItemGoods storageItemGoods);

    /**
     * 获取仓库货位信息
     * @param goodsid
     * @param storageid
     * @return com.hd.agent.basefiles.model.StorageItem
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public StorageItemGoods getStorageItemGoods(@Param("goodsid") String goodsid, @Param("storageid") String storageid);

    /**
     * 修改商品货位
     * @param storageItemGoods
     * @return int
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public int editStorageItemGoods(StorageItemGoods storageItemGoods);

    /**
     * 删除仓库商品货位
     * @param storageid
     * @param goodsid
     * @return int
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public int deleteStorageItemGoods(@Param("storageid") String storageid, @Param("goodsid") String goodsid);

    /**
     * 获取仓库商品货位的仓库信息
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Nov 27, 2017
     */
    public List showStorageInfoList(PageMap pageMap);

    /**
     * 获取仓库商品货位的仓库信息
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Nov 27, 2017
     */
    public int showStorageInfoCount(PageMap pageMap);
}
