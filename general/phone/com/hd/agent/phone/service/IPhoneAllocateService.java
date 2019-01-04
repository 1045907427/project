package com.hd.agent.phone.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 手机调拨申请service
 * Created by chenwei on 2016-07-23.
 */
public interface IPhoneAllocateService {
    /**
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData searchStorageGoodsList(PageMap pageMap) throws Exception;

    /**
     * 根据仓库编号和商品编号 获取仓库商品的相关信息
     * @param storageid
     * @param goodsid
     * @return
     * @throws Exception
     */
    public Map getStorageGoodsInfo(String storageid,String goodsid) throws Exception;

    /**
     * 添加调拨申请单
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public Map addAllocateNotice(JSONObject jsonObject) throws Exception;

    /**
     * 获取调拨通知单列表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public List getAllocateNoticeList(PageMap pageMap) throws Exception;

    /**
     * 根据单据编号 获取调拨通知单详细信息
     * @param billid
     * @return
     * @throws Exception
     */
    public Map getAllocateNoticeInfo(String billid) throws Exception;

    /**
     * 根据仓库编号获取该仓库的商品库存列表
     * @param storageid
     * @return
     * @throws Exception
     */
    public List getStorageGoodsByAllAllocate(String storageid) throws Exception;

    /**
     * 调拨单审核出库
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 08, 2017
     */
    public Map auditAllocateStorageOut(String id) throws Exception;

    /**
     * 调拨单审核入库
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 08, 2017
     */
    public Map auditAllocatStorageEnter(String id) throws Exception;
    /**
     * 添加调拨出库单
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public Map addAllocateOut(JSONObject jsonObject,String addtype) throws Exception;
    /**
     * 获取调拨出库单列表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public List getAllocateOutList(PageMap pageMap) throws Exception;

    /**
     * 根据单据编号 获取调拨出库单详细信息
     * @param billid
     * @return
     * @throws Exception
     */
    public Map getAllocateOutInfo(String billid) throws Exception;
    /**
     * 获取调拨出库单明细列表
     * @param billid
     * @return
     * @throws Exception
     */
    public List getAllocateOutDetail(String billid) throws Exception;

}
