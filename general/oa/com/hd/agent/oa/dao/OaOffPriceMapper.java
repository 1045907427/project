package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaOffPrice;
import com.hd.agent.oa.model.OaOffPriceDetail;

public interface OaOffPriceMapper {

    /**
     * 删除批量特价调整单
     * @param id
     * @return
     * @author limin
     * @date Mar 6, 2015
     */
    public int deleteOaOffPrice(@Param("id")String id);

    /**
     * 增加批量特价调整单
     * @param price
     * @return
     * @author limin
     * @date Mar 6, 2015
     */
    public int insertOaOffPrice(OaOffPrice price);

    /**
     * 修改批量特价调整单
     * @param price
     * @return
     * @author limin
     * @date Mar 6, 2015
     */
    public int updateOaOffPrice(OaOffPrice price);

    /**
     * 查询批量特价调整单
     * @param id
     * @return
     * @author limin
     * @date Mar 6, 2015
     */
    public OaOffPrice selectOaOffPrice(@Param("id")String id);

    /**
     * 增加批量特价调整单明细
     * @param detail
     * @return
     * @author limin
     * @date Mar 6, 2015
     */
    public int insertOaOffPriceDetail(OaOffPriceDetail detail);

    /**
     * 根据billid删除对应的特价调整单明细
     * @param billid
     * @return
     */
    public int deleteOaOffPriceDetailByBillid(@Param("billid")String billid);

    /**
     * 查询批量特价调整单明细
     * @param map
     * @return
     * @author limin
     * @date Mar 6, 2015
     */
    public List<OaOffPrice> selectOaOffPriceDetailList(PageMap map);

    /**
     * 查询批量特价调整单明细Count
     * @param map
     * @return
     * @author limin
     * @date Mar 6, 2015
     */
    public int selectOaOffPriceDetailListCnt(PageMap map);

    /**
     * 查询批量特价调整单明细
     * @param billid
     * @return
     * @author limin
     * @date Mar 10, 2015
     */
    public List<OaOffPriceDetail> selectOaOffPriceDetailListByBillid(String billid);
}