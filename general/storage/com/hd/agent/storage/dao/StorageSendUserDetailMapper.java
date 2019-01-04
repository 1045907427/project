package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageSendUserDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StorageSendUserDetailMapper {

    /**
     * 获取发货人报表列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    List getSendUserDetailList(PageMap pageMap);

    /**
     * 获取发货人报表列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    List getSendUserDetailListSum(PageMap pageMap);

    /**
     * 获取发货人报表数量
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    int getSendUserDetailListCount(PageMap pageMap);
    /**
     * 新增发货人报表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    int addSendUserDetail(StorageSendUserDetail storageSendUserDetail);


    /**
     * 修改发货人报表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    int updateStorageSendUserDetail(StorageSendUserDetail storageSendUserDetail);


    /**
     * 获取发货人报表列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    List getSendUserDetailListBySenduser(PageMap pageMap);

    /**
     * 获取发货人报表数量
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    int getSendUserDetailListCountBySenduser(PageMap pageMap);


    /**
     * 根据单据编号和类型删除发货人报表明细
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public int deleteSendUserDetail(@Param("sourceid") String sourceid,@Param("billtype") String billtype);
}