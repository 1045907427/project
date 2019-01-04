package com.hd.agent.storage.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageSendUserOrder;

import java.util.Map;

/**
 * Created by wanghongteng on 2016/10/26.
 */
public interface IStorageSendUserService {

    /**
     * 获取发货人单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    PageData showSendUserOrderList(PageMap pageMap) throws Exception;

    /**
     * 获取待分配核对发货单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    PageData showOrderList(PageMap pageMap,String billtype) throws Exception;


    /**
     * 发货人分配
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public Map SendUserOrderAssigned(StorageSendUserOrder storageSendUserOrder, String storager, String billtype) throws Exception;





    /**
     * 获取发货人报表列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    PageData showSendUserDetailList(PageMap pageMap) throws Exception;

    /**
     * 获取发货人报表日志列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    PageData getSendUserDetailLogBySendUser(PageMap pageMap) throws Exception;

    /**
     * 删除发货人报表数据
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public Map deleteSendUserOrder(Map map) throws  Exception;
}
