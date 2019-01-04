package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageSendUserOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StorageSendUserOrderMapper {
    /**
     * 获取发货人单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    List getSendUserOrderList(PageMap pageMap);

    /**
     * 获取发货人单据数量
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    int getSendUserOrderListCount(PageMap pageMap);
    /**
     * 新增发货人单据数据
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    int addSendUserOrder(StorageSendUserOrder storageSendUserOrder);

    /**
     * 根据单据编号和类型删除发货人单据
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public int deleteSendUserOrder(@Param("sourceid") String sourceid,@Param("billtype") String billtype);

    /**
     *  获取待分配核对发货单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public List getCheckOrderList(PageMap pageMap);

    /**
     *  获取待分配核对发货单数量
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public int getCheckOrderListCount(PageMap pageMap);
    /**
     * 获取待分配直接上车单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    List getLoadedOrderList(PageMap pageMap);

    /**
     *  获取待分配直接上车单据数量
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public int getLoadedOrderListCount(PageMap pageMap);
    /**
     * 获取待分配卸货统计单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    List getUnloadedOrderList(PageMap pageMap);
    /**
     *  获取待分配卸货统计单据数量
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public int getUnloadedOrderListCount(PageMap pageMap);


    /**
     * 根据编码获取配置参数
     * @param sourceid,billtype
     * @return
     */
    StorageSendUserOrder getStorageSendUserOrderBySourceidAndBilltype(@Param("sourceid") String sourceid,@Param("billtype") String billtype);
}