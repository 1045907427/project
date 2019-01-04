package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.Bom;
import com.hd.agent.basefiles.model.BomDetail;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Bom Mapper
 * @author limin
 * @date Sep 21, 2015
 */
public interface BomMapper {

    /**
     * 新增BOM规则
     * @param bom
     * @return
     * @author limin
     * @date Sep 21, 2015
     */
    public int insertBom(Bom bom);

    /**
     * 更新BOM规则
     * @param bom
     * @return
     * @author limin
     * @date Sep 21, 2015
     */
    public int updateBom(Bom bom);

    /**
     * 更新BOM规则
     * @param id
     * @return
     * @author limin
     * @date Sep 21, 2015
     */
    public int deleteBom(@Param("id")String id);

    /**
     * 新增BOM规则明细
     * @param detail
     * @return
     * @author limin
     * @date Sep 21, 2015
     */
    public int insertBomDetail(BomDetail detail);

    /**
     * 新增BOM规则明细
     * @param billid
     * @return
     * @author limin
     * @date Sep 21, 2015
     */
    public int deleteBomDetail(@Param("billid")String billid);

    /**
     * 查询BOM List
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 21, 2015
     */
    public List<Bom> selectBomList(PageMap pageMap);

    /**
     * 查询BOM List Count
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 21, 2015
     */
    public int selectBomListCount(PageMap pageMap);

    /**
     * 查询BOM
     * @param id
     * @return
     * @author limin
     * @date Sep 24, 2015
     */
    public Bom selectBom(@Param("id")String id);

    /**
     * 查询BOM商品明细
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 24, 2015
     */
    public List<Map> selectBomDetailList(PageMap pageMap);

    /**
     * 查询BOM商品明细Count
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 24, 2015
     */
    public int selectBomDetailListCount(PageMap pageMap);
}