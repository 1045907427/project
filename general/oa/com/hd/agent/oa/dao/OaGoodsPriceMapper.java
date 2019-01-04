package com.hd.agent.oa.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaGoodsPrice;
import com.hd.agent.oa.model.OaGoodsPriceDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaGoodsPriceMapper {

    /**
     *
     * @param id
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public OaGoodsPrice selectOaGoodsPrice(@Param("id")String id);

    /**
     *
     * @param id
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public int deleteOaGoodsPrice(@Param("id")String id);

    /**
     *
     * @param goods
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public int insertOaGoodsPrice(OaGoodsPrice goods);

    /**
     *
     * @param goods
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public int updateOaGoodsPrice(OaGoodsPrice goods);

    /**
     *
     * @param detail
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public int insertOaGoodsPriceDetail(OaGoodsPriceDetail detail);

    /**
     *
     * @param billid
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public int deleteOaGoodsDetailByBillid(@Param("billid")String billid);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public List<OaGoodsPriceDetail> selectGoodsPriceDetailList(PageMap map);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date May 27, 2015
     */
    public int selectGoodsPriceDetailListCount(PageMap map);

    public List<OaGoodsPriceDetail> selectOaGoodsPriceDetailListByBillid(String billid);

}