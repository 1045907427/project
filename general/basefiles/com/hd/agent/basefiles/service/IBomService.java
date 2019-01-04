package com.hd.agent.basefiles.service;

import com.hd.agent.basefiles.model.Bom;
import com.hd.agent.basefiles.model.BomDetail;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;

/**
 * BOM Service
 * @author limin
 * @date Sep 21, 2015
 */
public interface IBomService {

    /**
     * 查询BOM列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 21, 2015
     */
    public PageData selectBomList(PageMap pageMap) throws Exception;

    /**
     * 新增BOM
     * @param bom
     * @param list
     * @return
     * @author limin
     * @date Sep 23, 2015
     */
    public int addBom(Bom bom, List<BomDetail> list);

    /**
     * 修改BOM
     * @param bom
     * @param list
     * @return
     * @author limin
     * @date Sep 23, 2015
     */
    public int editBom(Bom bom, List<BomDetail> list);

    /**
     * 查询BOM
     * @param id
     * @return
     * @author limin
     * @date Sep 24, 2015
     */
    public Bom selectBom(String id);

    /**
     * 查询BOM明细列表
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 24, 2015
     */
    public PageData selectBomDetailList(PageMap pageMap);

    /**
     * 变更BOM状态
     * @param bom
     * @return
     * @author limin
     * @date Sep 24, 2015
     */
    public int updateBomStatus(Bom bom);

    /**
     * 删除BOM
     * @param id
     * @return
     * @author limin
     * @date Sep 24, 2015
     */
    public int deleteBom(String id);
}
